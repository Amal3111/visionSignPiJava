package dtos;


import entities.Cours;
import entities.UserCours;
import services.cours.CoursService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserCoursDto {
    public UserCoursDto() {
    }

    public UserCours onlyItemSingle(ResultSet rs) throws SQLException {
        UserCours l =new UserCours();

        l.setId(rs.getInt("id"));
        l.setCoursId(rs.getInt("coursId"));
        l.setUserId(rs.getInt("userId"));
        l.setCorrectQuizz(rs.getBoolean("isCorrectQuizz"));
        l.setStage(rs.getInt("stage"));
        l.setCompleted(rs.getBoolean("isCompleted"));
        l.setEnrollmentDate(rs.getDate("enrollmentDate"));
        l.setCompletedDate(rs.getDate("completedDate"));




        return l ;
    }

    public List<UserCours> list(ResultSet rs) throws  SQLException{


        List<UserCours> lessons =new ArrayList<>();


        while (rs.next()){


            lessons.add(single(rs));
        }

        return lessons ;


    }



    public List<UserCours> listWithCours(ResultSet rs) throws  SQLException{


        List<UserCours> lessons =new ArrayList<>();


        while (rs.next()){


            lessons.add(single(rs));
        }

        return lessons ;


    }
    public UserCours single(ResultSet rs) throws SQLException {


        UserCours l = onlyItemSingle(rs);


        CoursService coursService = new CoursService() ;

        Cours cours =  coursService.getById(l.getCoursId());

        l.setUserCours(cours);




        return l ;
    }



}
