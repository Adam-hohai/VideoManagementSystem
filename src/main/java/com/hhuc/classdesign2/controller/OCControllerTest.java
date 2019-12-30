package com.hhuc.classdesign2.controller;

import com.hhuc.classdesign2.javabean.*;
import com.hhuc.classdesign2.service.OCServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 用于测试数据库操作是否成功
 */

@ResponseBody
@Controller
public class OCControllerTest {

    @Autowired
    OCServiceImpl ocService;

    @RequestMapping(value = "/StudentInfo/{studentNo}",method = RequestMethod.GET)
    public StudentInfo getStuByNo(@PathVariable("studentNo") String studentNo){
        return ocService.getStuByNo(studentNo);
    }

    @RequestMapping(value = "/insertSchool",method = RequestMethod.POST)
    public int insertSchool(SchoolInfo schoolInfo){
        return ocService.insertSchool(schoolInfo);
    }

    @RequestMapping(value = "/insertRegistration",method = RequestMethod.POST)
    public int insertRegistration(Registration registration){
        return ocService.insertRegistration(registration);
    }

    @RequestMapping(value = "/insertTeacher",method = RequestMethod.POST)
    public int insertTeacher(TeacherInfo teacherInfo,String email,String schoolName){
        return ocService.insertTeacher(teacherInfo,email,schoolName);
    }

    @GetMapping(value = "/getRegByEmail")
    public Long getRegByEmail(String email){
        return ocService.getRegByEmail(email).getRegistrationId();
    }

    @PostMapping(value = "/updateReg")
    public int updateRegistration(Registration registration){
        return ocService.updateRegistration(registration);
    }

    @PostMapping("/insertCourse")
    public int insertCourse(CourseInfo courseInfo, String email, String schoolName){
        return ocService.insertCourse(courseInfo,email,schoolName);
    }

    @PostMapping("/insertCheck")
    public int insertCheck(CheckInfo checkInfo){
        return ocService.insertCheck(checkInfo);
    }
}
