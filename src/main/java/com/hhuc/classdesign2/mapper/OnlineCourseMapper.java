package com.hhuc.classdesign2.mapper;

import com.hhuc.classdesign2.javabean.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OnlineCourseMapper {

    @Select("select * from SchoolInfo")
    public List<SchoolInfo> getSchool();

    @Select("select * from CourseInfo")
    public List<CourseInfo> getCourse();

    @Select("select * from HistoryInfo")
    public List<HistoryInfo> getHistory();

    @Select("select * from RegistrationInfo")
    public List<Registration> getRegistration();

    @Select("select * from StudentInfo")
    public List<StudentInfo> getStudent();

    @Select("select * from SubscriptionInfo")
    public List<SubscriptionInfo> getSubscription();

    @Select("select * from TeacherInfo")
    public List<TeacherInfo> getTeacher();

    @Select("select * from AdminInfo")
    public List<AdminInfo> getAdmin();

    @Select("select * from CheckInfo")
    public List<CheckInfo> getCheck();

    @Select("select * from AdminInfo where adminNo=#{adminNo}")
    public AdminInfo getAdminByNo(String adminNo);

    @Select("select * from StudentInfo where studentNo=#{studentNo}")
    public StudentInfo getStuByNo(String studentNo);

    //通过registrationId获得StudentInfo
    @Select("select * from StudentInfo where registrationId=#{registrationId}")
    public StudentInfo getStuByRegId(Long registrationId);

    @Select("select * from TeacherInfo where registrationId=#{registrationId}")
    public TeacherInfo getTeaByRegId(Long registrationId);

    @Select("select * from SchoolInfo where schoolId=#{schoolId}")
    public SchoolInfo getSchById(Long schoolId);

    @Select("select *  from Registration where registrationId=#{registrationId}")
    public Registration getRegById(Long registrationId);

    @Select("select * from CourseInfo where schoolId=#{schoolId}")
    public List<CourseInfo> getCouBySchId(Long schoolId);

    @Select("select * from CourseInfo where courseId=#{courseId}")
    public CourseInfo getCouById(Long courseId);

    @Select("select * from CourseInfo where teacherId=#{teacherId}")
    public List<CourseInfo> getCouByTeaId(Long teacherId);

    @Select("select * from SchoolInfo where schoolName=#{schoolName}")
    public SchoolInfo getSchByName(String schoolName);

    //courseName模糊查询所有CourseInfo
    @Select("select * from CourseInfo where courseName like '%${courseName}%'")
    public List<CourseInfo> getCouByName(String courseName);

    @Select("select * from Registration where email=#{email}")
    public Registration getRegByEmail(String email);

    @Select("select * from TeacherInfo where teacherNo=#{teacherNo}")
    public TeacherInfo getTeaByNo(String teacherNo);

    @Select("select * from SubscriptionInfo where studentId=#{studentId}")
    public List<SubscriptionInfo> getSubByStuId(Long studentId);

    @Select("select * from HistoryInfo where studentId=#{studentId}")
    public List<HistoryInfo> getHisByStuId(Long studentId);

    @Select("select max(courseId) from CourseInfo")
    public Long getMaxCouId();

    @Select("select * from CheckInfo where status=#{status}")
    public List<CheckInfo> getCheckByStatus(Integer status);

    @Select("select * from CheckInfo where courseId=#{courseId}")
    public CheckInfo getCheckByCouId(Long courseId);

    @Options(useGeneratedKeys = true,keyProperty = "studentId")
    @Insert("insert into StudentInfo(schoolId,registrationId,studentName,studentNo,specialty) values(#{schoolId},#{registrationId},#{studentName},#{studentNo},#{specialty})")
    public int insertStudent(StudentInfo studentInfo);

    @Options(useGeneratedKeys = true,keyProperty = "schoolId")
    @Insert("insert into SchoolInfo(schoolName) values(#{schoolName})")
    public int insertSchool(SchoolInfo schoolInfo);

    @Options(useGeneratedKeys = true,keyProperty = "courseId")
    @Insert("insert into CourseInfo(teacherId,schoolId,courseName,courseNo,specialty,clickCount) values(#{teacherId},#{schoolId},#{courseName},#{courseNo},#{specialty},#{clickCount})")
    public int insertCourse(CourseInfo courseInfo);

    @Options(useGeneratedKeys = true,keyProperty = "historyId")
    @Insert("insert into HistoryInfo(studentId,courseId,clickTime) values(#{studentId},#{courseId},#{clickTime})")
    public int insertHistory(HistoryInfo historyInfo);

    @Options(useGeneratedKeys = true,keyProperty = "registrationId")
    @Insert("insert into Registration(email,password) values(#{email},#{password})")
    public int insertRegistration(Registration registration);

    @Options(useGeneratedKeys = true,keyProperty = "subscriptionId")
    @Insert("insert into SubscriptionInfo(studentId,courseId,subscriptionTime) values(#{studentId},#{courseId},#{subscriptionTime})")
    public int insertSubscription(SubscriptionInfo subscriptionInfo);

    @Options(useGeneratedKeys = true,keyProperty = "teacherId")
    @Insert("insert into TeacherInfo(schoolId,registrationId,teacherNo,teacherName) values(#{schoolId},#{registrationId},#{teacherNo},#{teacherName})")
    public int insertTeacher(TeacherInfo teacherInfo);

    @Options(useGeneratedKeys = true,keyProperty = "checkId")
    @Insert("insert into CheckInfo(courseId,adminId,status) values(#{courseId},#{adminId},#{status})")
    public int insertCheck(CheckInfo checkInfo);

    @Update("update Registration set password=#{password} where email=#{email}")
    public int updateRegistration(Registration registration);

    @Update("update CourseInfo set clickCount=clickCount+1 where courseId=#{courseId}")
    public int updateCourseInfo(Long courseId);

    @Update("update CheckInfo set adminId=#{adminId},status=#{status} where courseId=#{courseId}")
    public int updateCheck(CheckInfo checkInfo);

    @Delete("delete from SubscriptionInfo where courseId=#{courseId} and studentId=#{studentId}")
    public int deleteSubByCouIdStuId(Long courseId,Long studentId);

    @Delete("delete from SubscriptionInfo where courseId=#{courseId}")
    public int deleteSubByCouId(Long courseId);

    @Delete("delete from HistoryInfo where courseId=#{courseId} and studentId=#{studentId}")
    public int deleteHisByCouIdStuId(Long courseId,Long studentId);

    @Delete("delete from HistoryInfo where courseId=#{courseId}")
    public int deleteHisByCouId(Long courseId);

    @Delete("delete from CourseInfo where courseId=#{courseId}")
    public int deleteCourseById(Long courseId);

    @Delete("delete from CheckInfo where courseId=#{courseId}")
    public int deleteCheckByCouId(Long courseId);
}
