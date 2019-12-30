package com.hhuc.classdesign2.controller;

import com.hhuc.classdesign2.javabean.*;
import com.hhuc.classdesign2.mapper.OnlineCourseMapper;
import com.hhuc.classdesign2.service.OCServiceImpl;
import com.hhuc.classdesign2.session.ConstantUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

@Controller
public class OCController {

    @Autowired
    OCServiceImpl ocService;

    //学生注册链接点击后跳转
    @GetMapping("/studentReg")
    public String stuRegClick(){
        return "studentReg";
    }

    //教师注册链接点击后跳转
    @GetMapping("/teacherReg")
    public String teaRegClick(){
        return "teacherReg";
    }

    //学生注册时传来表单信息，注册后跳转到登录
    @PostMapping("/insertStuReg")
    @Transactional
    public String insertStuReg(HttpServletRequest request,StudentInfo studentInfo,Registration registration,SchoolInfo schoolInfo){
        ocService.insertRegistration(registration);
        if (ocService.getSchByName(request.getParameter("schoolName")) == null){
            ocService.insertSchool(schoolInfo);
        }
        ocService.insertStudent(studentInfo,request.getParameter("email"),request.getParameter("schoolName"));
        return "login";
    }

    //教师注册时传来表单信息，注册后跳转到登录
    @PostMapping("/insertTeaReg")
    public String insertTeaReg(HttpServletRequest request,TeacherInfo teacherInfo,Registration registration,SchoolInfo schoolInfo){
        ocService.insertRegistration(registration);
        if (ocService.getSchByName(request.getParameter("schoolName")) == null){
            ocService.insertSchool(schoolInfo);
        }
        ocService.insertTeacher(teacherInfo,request.getParameter("email"),request.getParameter("schoolName"));
        return "login";
    }

    //接受表单传来的邮箱和密码
    @PostMapping(value = "/Page")
    public String getEmailPassword(HttpServletRequest request, Model model){
        String user = request.getParameter("option");
        String email = request.getParameter("email");

//        //存入email
//        ConstantUtils.email = email;

        HttpSession session = request.getSession();
        session.setAttribute("email",email);

        String password = request.getParameter("password");
        Registration registration = ocService.getRegByEmail(email);
//        //Session存储用户信息
//        if(registration != null){
//            request.getSession().setAttribute(ConstantUtils.USER_SESSION_KEY,registration);
//        }

//        ResultInfo result = new ResultInfo();
//        if(registration.getPassword().equals(password)){
//            result.setSuccess(true);
//        }else result.setSuccess(false);
        model.addAttribute("email", email);
        if(registration.getPassword().equals(password)){
            if(user.equals("学生")){
                //需要返回视频课程信息
                List<CheckInfo> checkInfoList = ocService.getCheckByStatus(1);
                List<CourseInfo> courseInfoList = new ArrayList<>();
                for(CheckInfo checkInfo:checkInfoList){
                    CourseInfo courseInfo = ocService.getCouById(checkInfo.getCourseId());
                    courseInfoList.add(courseInfo);
                }
                model.addAttribute("courseInfos",courseInfoList);
                return "studentPage";
            }
            else{
                TeacherInfo teacherInfo = ocService.getTeaByEmail(email);
                List<CourseInfo> courseInfoList = ocService.getCouByTeaId(teacherInfo.getTeacherId());
                List<CourseInfo> checkedCourses = new ArrayList<>();
                List<CourseInfo> uncheckedCourses = new ArrayList<>();
                for (CourseInfo courseInfo:courseInfoList){
                    CheckInfo checkInfo = ocService.getCheckByCouId(courseInfo.getCourseId());
                    if (checkInfo.getStatus() == 1) checkedCourses.add(courseInfo);
                    else uncheckedCourses.add(courseInfo);
                }
                model.addAttribute("checkedCourses",checkedCourses);
                model.addAttribute("uncheckedCourses",uncheckedCourses);
                return "teacherPage";
            }
        }else return "login";

    }

    //搜索时跳转
    @PostMapping("/searchCourses")
    public String searchCourses(HttpServletRequest request,Model model){
        List<Long> couIdList = ocService.getCouIdByName(request.getParameter("searchName"));
        List<CourseInfo> courseInfoList = new ArrayList<>();
        for(Long courseId:couIdList){
            CourseInfo courseInfo = ocService.getCouById(courseId);
            courseInfoList.add(courseInfo);
        }
        model.addAttribute("courseInfos",courseInfoList);
        return "searchCourses";
    }

    //点击视频链接后接受Ajax传递的courseId，新增数据库中的HistoryInfo，并且更新CourseInfo中的点击数
    @PostMapping("/clickCourseHref")
    @ResponseBody
    public void clickCourseHref(HttpServletRequest request){
        HttpSession session = request.getSession();
        StudentInfo studentInfo = ocService.getStuByEmail(session.getAttribute("email").toString());
        List<HistoryInfo> historyInfoList = ocService.getHistory();
        boolean historyExist = false;//是否存在此条记录
        for (HistoryInfo historyInfo:historyInfoList){
            if(historyInfo.getStudentId().equals(studentInfo.getStudentId()) && request.getParameter("courseId").equals(historyInfo.getCourseId().toString())){
                historyExist = true;
            }
        }
        //不存在就新增
        Long courseId = Long.parseLong(request.getParameter("courseId"));
        if(!historyExist){
            HistoryInfo historyInfo = new HistoryInfo();
            historyInfo.setStudentId(studentInfo.getStudentId());
            historyInfo.setCourseId(courseId);
            ocService.insertHistory(historyInfo);
        }
        //点击量加一
        ocService.updateCourseInfo(courseId);
//        ResultInfo resultInfo = new ResultInfo();
//        if(changeNum >= 1){
//            resultInfo.setSuccess(true);
//        }else resultInfo.setSuccess(false);
//        return resultInfo;
    }

    //点击收藏后接受ajax传来的courseId，向SubscriptionInfo插入
    @PostMapping("/subscriptionClick")
    @ResponseBody
    public void subscriptionClick(HttpServletRequest request){
        HttpSession session = request.getSession();
        StudentInfo studentInfo = ocService.getStuByEmail(session.getAttribute("email").toString());
        List<SubscriptionInfo> subscriptionInfoList = ocService.getSubscription();
        boolean subscriptionExist = false;
        for(SubscriptionInfo subscriptionInfo:subscriptionInfoList){
            if(subscriptionInfo.getStudentId().equals(studentInfo.getStudentId()) && request.getParameter("courseId").equals(subscriptionInfo.getCourseId().toString())){
                subscriptionExist = true;
            }
        }
        //不存在就新增
        Long courseId = Long.parseLong(request.getParameter("courseId"));
        if(!subscriptionExist){
            SubscriptionInfo subscriptionInfo = new SubscriptionInfo();
            subscriptionInfo.setCourseId(courseId);
            subscriptionInfo.setStudentId(studentInfo.getStudentId());
            ocService.insertSubscription(subscriptionInfo);
        }
    }

    @RequestMapping("/")
    public String index(){
        return "login";
    }

    //登录界面
    @RequestMapping("/login")
    public String login(){
//        model.addAttribute("password","11111");
        return "login";
    }

    //学生主页面测试
    @RequestMapping("/studentPageTest")
    public String studentPage(){
        return "studentPage";
    }

    //教师主页面
    @RequestMapping("/teacherPage")
    public String teacherPage(){
        return "teacherPage";
    }

    //我的收藏课程 主页面
    @RequestMapping("/myCourses")
    public String myCourse(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        StudentInfo studentInfo = ocService.getStuByEmail(session.getAttribute("email").toString());
        List<SubscriptionInfo> subscriptionInfoList = ocService.getSubByStuId(studentInfo.getStudentId());
        Map<Long,String> nameMap = new HashMap<>();
        Map<Long,String> specialtyMap = new HashMap<>();
        for(SubscriptionInfo subscriptionInfo:subscriptionInfoList){
            CourseInfo courseInfo = ocService.getCouById(subscriptionInfo.getCourseId());
            nameMap.put(courseInfo.getCourseId(),courseInfo.getCourseName());
            specialtyMap.put(courseInfo.getCourseId(),courseInfo.getSpecialty());
        }
        model.addAttribute("courseNames",nameMap);
        model.addAttribute("specialties",specialtyMap);
        model.addAttribute("subscriptionList",subscriptionInfoList);
        return "myCourses";
    }

    //删除收藏
    @PostMapping("/deleteSubscription")
    @ResponseBody
    public ResultInfo deleteSubscription(HttpServletRequest request){
        HttpSession session = request.getSession();
        StudentInfo studentInfo = ocService.getStuByEmail(session.getAttribute("email").toString());
        Long courseId = Long.parseLong(request.getParameter("courseId"));
        int effectRow = ocService.deleteSubByCouIdStuId(courseId,studentInfo.getStudentId());
        ResultInfo result = new ResultInfo();
        if(effectRow == 1) result.setSuccess(true);
        else result.setSuccess(false);
        return result;
    }

    //观看历史页面
    @RequestMapping("/history")
    public String history(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        StudentInfo studentInfo = ocService.getStuByEmail(session.getAttribute("email").toString());
        List<HistoryInfo> historyInfoList = ocService.getHisByStuId(studentInfo.getStudentId());
        Map<Long,String> nameMap = new HashMap<>();
        Map<Long,String> specialtyMap = new HashMap<>();
        for (HistoryInfo historyInfo:historyInfoList){
            CourseInfo courseInfo = ocService.getCouById(historyInfo.getCourseId());
            nameMap.put(courseInfo.getCourseId(),courseInfo.getCourseName());
            specialtyMap.put(courseInfo.getCourseId(),courseInfo.getSpecialty());
        }
        model.addAttribute("courseNames",nameMap);
        model.addAttribute("specialties",specialtyMap);
        model.addAttribute("historyList",historyInfoList);
        return "history";
    }

    //学生页面删除历史，根据课程id和学生id
    @PostMapping("/deleteHistory")
    @ResponseBody
    public ResultInfo deleteHistory(HttpServletRequest request){
        HttpSession session = request.getSession();
        StudentInfo studentInfo = ocService.getStuByEmail(session.getAttribute("email").toString());
        Long courseId = Long.parseLong(request.getParameter("courseId"));
        int effectRow = ocService.deleteHisByCouIdStuId(courseId,studentInfo.getStudentId());
        ResultInfo result = new ResultInfo();
        if(effectRow == 1) result.setSuccess(true);
        else result.setSuccess(false);
        return result;
    }

    //学生个人信息页面
    @RequestMapping("/studentInfo")
    public String studentInfo(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Map<String,Object> map = ocService.getAllStu(session.getAttribute("email").toString());
        model.addAttribute("email",session.getAttribute("email").toString());
        model.addAttribute("studentName",map.get("studentName"));
        model.addAttribute("studentNo",map.get("studentNo"));
        model.addAttribute("specialty",map.get("specialty"));
        model.addAttribute("schoolName",map.get("schoolName"));
        return "studentInfo";
    }

    //修改密码
    @PostMapping("/modifyPassword")
    @ResponseBody
    public ResultInfo modifyPassword(HttpServletRequest request){
        HttpSession session = request.getSession();
        Registration registration = ocService.getRegByEmail(session.getAttribute("email").toString());
        ResultInfo result = new ResultInfo();
        if (registration.getPassword().equals(request.getParameter("password"))){
            Registration newReg = new Registration();
            newReg.setEmail(session.getAttribute("email").toString());
            newReg.setPassword(request.getParameter("newPassword"));
            ocService.updateRegistration(newReg);
            result.setSuccess(true);
        }else result.setSuccess(false);
        return result;
    }

    //老师个人信息页面
    @RequestMapping("/teacherInfo")
    public String teacherInfo(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Map<String,Object> map = ocService.getAllTea(session.getAttribute("email").toString());
        model.addAttribute("email",session.getAttribute("email").toString());
        model.addAttribute("teacherName",map.get("teacherName"));
        model.addAttribute("teacherNo",map.get("teacherNo"));
        model.addAttribute("schoolName",map.get("schoolName"));
        return "teacherInfo";
    }

    //上传视频页面跳转
    @RequestMapping("/uploadPage")
    public String uploadPage(){
        return "uploadPage";
    }

    //教师上传视频
    @RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveVideo(@RequestParam("file") MultipartFile file)
            throws IllegalStateException {
        ResultInfo result = new ResultInfo();
//        ocService.insertCourse(courseInfo,request.getParameter("email"),request.getParameter("schoolName"));
        Long courseId = ocService.getMaxCouId();

        try{
            //插入视频
            //获取文件后缀
            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                    .toLowerCase();
            // 重构文件名称
            String pikId = "video" + courseId;
            String newVideoName = pikId + "." + fileExt;
            //保存视频
            String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\video";
            File fileSave = new File(uploadPath, newVideoName);
            file.transferTo(fileSave);

            //插入视频第一帧图片
            //Frame对象
            Frame frame = null;
            //标识
            int flag = 0;
            /*
            获取视频文件
            */
            FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\video\\video"+courseId+".mp4");
            fFmpegFrameGrabber.start();

            //获取视频总帧数
            int ftp = fFmpegFrameGrabber.getLengthInFrames();
            System.out.println("总帧数"+ftp);
            System.out.println("时长 " + ftp / fFmpegFrameGrabber.getFrameRate() / 60);

            while (flag <= ftp) {
                frame = fFmpegFrameGrabber.grabImage();
				/*
				对视频的第(总帧数除以10)帧进行处理
				 */
                if (frame != null && flag==ftp/10) {
                    //文件绝对路径+名字
                    String fileName =System.getProperty("user.dir")+"\\src\\main\\resources\\static\\img\\img"+courseId+".jpg";

                    //文件储存对象
                    File outPut = new File(fileName);
                    //创建BufferedImage对象
                    Java2DFrameConverter converter = new Java2DFrameConverter();
                    BufferedImage bufferedImage = converter.getBufferedImage(frame);
                    ImageIO.write(bufferedImage, "jpg", outPut);

                    break;
                }
                flag++;
            }
            fFmpegFrameGrabber.stop();
            fFmpegFrameGrabber.close();

            result.setSuccess(true);
            return  result;

        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            return  result;

        }


    }

    //教师上传视频后更新数据库
    @PostMapping("/uploadInfo")
    @Transactional
    @ResponseBody
    public ResultInfo uploadInfo(HttpServletRequest request,CourseInfo courseInfo){
        HttpSession session = request.getSession();
        ResultInfo result = new ResultInfo();
        TeacherInfo teacherInfo = ocService.getTeaByEmail(session.getAttribute("email").toString());
        SchoolInfo schoolInfo = ocService.getSchById(teacherInfo.getSchoolId());
        int effectNum = ocService.insertCourse(courseInfo,session.getAttribute("email").toString(),schoolInfo.getSchoolName());
        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setCourseId(ocService.getMaxCouId());
        checkInfo.setStatus(0);
        int effectNum2 = ocService.insertCheck(checkInfo);
        if (effectNum == 1 && effectNum2 == 1){
            result.setSuccess(true);
        }else result.setSuccess(false);
        return result;
    }

    //教师删除自己的视频，同时数据库中删掉收藏表和历史表和视频课程表里的信息
    @PostMapping("/deleteCourse")
    @Transactional
    @ResponseBody
    public ResultInfo deleteCourse(HttpServletRequest request){
        Long courseId = Long.parseLong(request.getParameter("courseId"));
        int effect1 = ocService.deleteHisByCouId(courseId);
        int effect2 = ocService.deleteSubByCouId(courseId);
        int effect3 = ocService.deleteCheckByCouId(courseId);
        int effect4 = ocService.deleteCourseById(courseId);
        ResultInfo result = new ResultInfo();
        if(effect1 >= 0 && effect2 >= 0 && effect3 == 1 && effect4 == 1) result.setSuccess(true);
        else result.setSuccess(false);
        return result;
    }

    //测试用的界面
    @RequestMapping("/test")
    public String test(){
        return "login";
    }

    //videoTest
    @RequestMapping("videoTest")
    public String videoTest(){
        return "videoTest";
    }

    //管理员登录页面
    @RequestMapping("/adminLogin")
    public String adminLogin(){
        return "adminLogin";
    }

    @PostMapping("/adminPage")
    public String adminPage(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        session.setAttribute("adminNo",request.getParameter("adminNo"));
        AdminInfo adminInfo = ocService.getAdminByNo(request.getParameter("adminNo"));
        if (adminInfo.getPassword().equals(request.getParameter("password"))){
            List<CheckInfo> checkInfoList = ocService.getCheckByStatus(0);
            List<CourseInfo> courseInfoList = new ArrayList<>();
            for(CheckInfo checkInfo:checkInfoList){
                CourseInfo courseInfo = ocService.getCouById(checkInfo.getCourseId());
                courseInfoList.add(courseInfo);
            }
            model.addAttribute("courseInfos",courseInfoList);
            return "adminPage";
        }else return "adminLogin";
    }

    @PostMapping("/checkCourseClick")
    @ResponseBody
    public ResultInfo checkCourseClick(HttpServletRequest request){
        HttpSession session = request.getSession();
        AdminInfo adminInfo = ocService.getAdminByNo(session.getAttribute("adminNo").toString());
        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setStatus(1);
        checkInfo.setCourseId(Long.parseLong(request.getParameter("courseId")));
        checkInfo.setAdminId(adminInfo.getAdminId());
        int effectNum = ocService.updateCheck(checkInfo);
        ResultInfo result = new ResultInfo();
        if (effectNum == 1) result.setSuccess(true);
        else result.setSuccess(false);
        return result;
    }
}
