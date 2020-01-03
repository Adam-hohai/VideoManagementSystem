package com.hhuc.classdesign2.service;

import com.hhuc.classdesign2.javabean.*;
import com.hhuc.classdesign2.mapper.OnlineCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 服务类，相关接口方法的实现
 */

@Service
public class OCServiceImpl {

    @Autowired
    OnlineCourseMapper onlineCourseMapper;

    //获得所有CourseInfo
    public List<CourseInfo> getCourse() {
        return onlineCourseMapper.getCourse();
    }

    //获得所有HistoryInfo
    public List<HistoryInfo> getHistory(){
        return onlineCourseMapper.getHistory();
    }

    //获得所有SubscriptionInfo
    public List<SubscriptionInfo> getSubscription(){
        return onlineCourseMapper.getSubscription();
    }

    //根据id获得CourseInfo
    public CourseInfo getCouById(Long courseId){
        return onlineCourseMapper.getCouById(courseId);
    }

    //通过studentNo获得Student
    public StudentInfo getStuByNo(String studentNo) {
        return onlineCourseMapper.getStuByNo(studentNo);
    }

    //通过email获得Registration
    public Registration getRegByEmail(String email) {
        return onlineCourseMapper.getRegByEmail(email);
    }

    //通过studentId获得SubscriptionInfo
    public List<SubscriptionInfo> getSubByStuId(Long studentId){
        return onlineCourseMapper.getSubByStuId(studentId);
    }

    //通过studentId获得HistoryInfo
    public List<HistoryInfo> getHisByStuId(Long studentId){
        return onlineCourseMapper.getHisByStuId(studentId);
    }

    //通过schoolId获得SchoolInfo
    public SchoolInfo getSchById(Long schoolId){
        return onlineCourseMapper.getSchById(schoolId);
    }

    //通过teacherId获得CourseInfo
    public List<CourseInfo> getCouByTeaId(Long teacherId){
        return onlineCourseMapper.getCouByTeaId(teacherId);
    }

    //获得courseInfo中最大的courseId
    public Long getMaxCouId(){
        return onlineCourseMapper.getMaxCouId();
    }

    public List<CourseInfo> getCouBySpecialty(String specialty){
        return onlineCourseMapper.getCouBySpecialty(specialty);
    }

    //根据id获得teacherInfo
    public TeacherInfo getTeaById(Long teacherId){
        return onlineCourseMapper.getTeaById(teacherId);
    }

    //获得所有学校
    public List<SchoolInfo> getSchool(){
        return onlineCourseMapper.getSchool();
    }

    //获得所有教师
    public List<TeacherInfo> getTeacher(){
        return onlineCourseMapper.getTeacher();
    }

    //通过email获得StudentInfo
    public StudentInfo getStuByEmail(String email){
        Registration registration = onlineCourseMapper.getRegByEmail(email);
        StudentInfo studentInfo = onlineCourseMapper.getStuByRegId(registration.getRegistrationId());
        return studentInfo;
    }

    //通过email获得TeacherInfo
    public TeacherInfo getTeaByEmail(String email){
        Registration registration = onlineCourseMapper.getRegByEmail(email);
        TeacherInfo teacherInfo = onlineCourseMapper.getTeaByRegId(registration.getRegistrationId());
        return teacherInfo;
    }

    //通过学校名获得SchoolInfo
    public SchoolInfo getSchByName(String schoolName){
        return onlineCourseMapper.getSchByName(schoolName);
    }

    //学生信息页面的用于显示学生的信息
    public Map<String, Object> getAllStu(String email) {
        Registration registration = onlineCourseMapper.getRegByEmail(email);
        StudentInfo studentInfo = onlineCourseMapper.getStuByRegId(registration.getRegistrationId());
        SchoolInfo schoolInfo = onlineCourseMapper.getSchById(studentInfo.getSchoolId());
        Map<String, Object> map = new HashMap();
        map.put("studentName", studentInfo.getStudentName());
        map.put("studentNo", studentInfo.getStudentNo());
        map.put("specialty", studentInfo.getSpecialty());
        map.put("email", email);
        map.put("password", registration.getPassword());
        map.put("schoolName", schoolInfo.getSchoolName());
        return map;
    }

    //教师信息页面的用于显示教师的信息
    public Map<String, Object> getAllTea(String email) {
        Registration registration = onlineCourseMapper.getRegByEmail(email);
        TeacherInfo teacherInfo = onlineCourseMapper.getTeaByRegId(registration.getRegistrationId());
        SchoolInfo schoolInfo = onlineCourseMapper.getSchById(teacherInfo.getSchoolId());
        Map<String, Object> map = new HashMap();
        map.put("teacherNo", teacherInfo.getTeacherNo());
        map.put("teacherName", teacherInfo.getTeacherName());
        map.put("schoolName", schoolInfo.getSchoolName());
        map.put("email", email);
        map.put("password", registration.getPassword());
        return map;
    }

    //插入School
    public int insertSchool(SchoolInfo schoolInfo) {
        return onlineCourseMapper.insertSchool(schoolInfo);
    }

    //插入Registration
    public int insertRegistration(Registration registration) {
        return onlineCourseMapper.insertRegistration(registration);
    }

    //插入TeacherInfo,外键分别通过schoolName和email得到
//    @Autowired
//    TeacherInfo teacherInfo;
    @Transactional
    public int insertTeacher(TeacherInfo teacherInfo, String email, String schoolName) {
        SchoolInfo schoolInfo = onlineCourseMapper.getSchByName(schoolName);
        Registration registration = onlineCourseMapper.getRegByEmail(email);
        teacherInfo.setRegistrationId(registration.getRegistrationId());
        teacherInfo.setSchoolId(schoolInfo.getSchoolId());
//        teacherInfo.setTeacherName(teacherName);
//        teacherInfo.setTeacherNo(teacherNo);
        return onlineCourseMapper.insertTeacher(teacherInfo);
    }

    //插入CourseInfo，外键通过teacherNo和schoolName得到
    @Transactional
    public int insertCourse(CourseInfo courseInfo, String email, String schoolName) {
        Registration registration = onlineCourseMapper.getRegByEmail(email);
        TeacherInfo teacherInfo = onlineCourseMapper.getTeaByRegId(registration.getRegistrationId());
        SchoolInfo schoolInfo = onlineCourseMapper.getSchByName(schoolName);
        courseInfo.setSchoolId(schoolInfo.getSchoolId());
        courseInfo.setTeacherId(teacherInfo.getTeacherId());
        courseInfo.setClickCount(0);
        return onlineCourseMapper.insertCourse(courseInfo);
    }

    //    插入StudentInfo,外键分别通过schoolName和email得到
    @Transactional
    public int insertStudent(StudentInfo studentInfo, String email, String schoolName) {
        SchoolInfo schoolInfo = onlineCourseMapper.getSchByName(schoolName);
        Registration registration = onlineCourseMapper.getRegByEmail(email);
        studentInfo.setSchoolId(schoolInfo.getSchoolId());
        studentInfo.setRegistrationId(registration.getRegistrationId());
        return onlineCourseMapper.insertStudent(studentInfo);
    }

    //插入HistoryInfo,这里的courseId可以直接得到，我准备把课程视频链接用courseId唯一标识
    @Transactional
    public int insertHistory(HistoryInfo historyInfo) {
//        StudentInfo studentInfo = onlineCourseMapper.getStuByNo(studentNo);
//        historyInfo.setStudentId(studentInfo.getStudentId());
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        historyInfo.setClickTime(form.format(new Date()));
        return onlineCourseMapper.insertHistory(historyInfo);
    }

    //插入SubscriptionInfo,这里的courseId可以直接得到
    @Transactional
    public int insertSubscription(SubscriptionInfo subscriptionInfo) {
//        StudentInfo studentInfo = onlineCourseMapper.getStuByNo(studentNo);
//        subscriptionInfo.setStudentId(studentInfo.getStudentId());
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        subscriptionInfo.setSubscriptionTime(form.format(new Date()));
        return onlineCourseMapper.insertSubscription(subscriptionInfo);
    }

    //修改登录信息（密码）
    public int updateRegistration(Registration registration) {
        return onlineCourseMapper.updateRegistration(registration);
    }

    //搜索功能，根据课程名模糊查询获取视频课程Id
    public List<Long> getCouIdByName(String searchName) {
        List<CourseInfo> courseInfoList = onlineCourseMapper.getCouByName(searchName);
//        SchoolInfo schoolInfo = onlineCourseMapper.getSchByName(searchName);
//        List<CourseInfo> courseInfoList2 = onlineCourseMapper.getCouBySchId(schoolInfo.getSchoolId());
        List<Long> longList = new ArrayList<>();
        for (CourseInfo courseInfo : courseInfoList) {
            longList.add(courseInfo.getCourseId());
        }
//        for (CourseInfo courseInfo : courseInfoList2) {
//            longList.add(courseInfo.getCourseId());
//        }
        return longList;
    }

    //点击视频后更新CourseInfo,就是点击量会加一
    public int updateCourseInfo(Long courseId){
        return onlineCourseMapper.updateCourseInfo(courseId);
    }

    //学生删除订阅视频信息,根据学生id和课程id
    public int deleteSubByCouIdStuId(Long courseId,Long studentId) {
        return onlineCourseMapper.deleteSubByCouIdStuId(courseId,studentId);
    }

    //根据课程id删除所有订阅课程
    public int deleteSubByCouId(Long courseId){
        return onlineCourseMapper.deleteSubByCouId(courseId);
    }

    //学生删除观看历史视频信息，根据学生id和课程id
    public int deleteHisByCouIdStuId(Long courseId,Long studentId) {
        return onlineCourseMapper.deleteHisByCouIdStuId(courseId,studentId);
    }

    //根据课程id删除所有历史课程
    public int deleteHisByCouId(Long courseId){
        return onlineCourseMapper.deleteHisByCouId(courseId);
    }

    //老师根据课程id删除课程
    public int deleteCourseById(Long courseId){
        return onlineCourseMapper.deleteCourseById(courseId);
    }

    //根据adminNo获得AdminInfo
    public AdminInfo getAdminByNo(String adminNo){
        return onlineCourseMapper.getAdminByNo(adminNo);
    }

    //插入CheckInfo
    public int insertCheck(CheckInfo checkInfo){
        return onlineCourseMapper.insertCheck(checkInfo);
    }

    //通过status查找CheckInfo
    public List<CheckInfo> getCheckByStatus(Integer status){
        return onlineCourseMapper.getCheckByStatus(status);
    }

    //更新CheckInfo
    public int updateCheck(CheckInfo checkInfo){
        return onlineCourseMapper.updateCheck(checkInfo);
    }

    public int deleteCheckByCouId(Long courseId){
        return onlineCourseMapper.deleteCheckByCouId(courseId);
    }

    //根据courseId获得CheckInfo
    public CheckInfo getCheckByCouId(Long courseId){
        return onlineCourseMapper.getCheckByCouId(courseId);
    }
}
