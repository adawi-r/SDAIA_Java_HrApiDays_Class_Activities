package org.example.DAO;

import org.example.models.Job;
import org.example.dto.JobFilterDto;
import java.sql.*;
import java.util.ArrayList;

public class JobDAO {
    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\Desktop\\sdaia_java_projects\\HrApiDay05\\hr.db";
    private static final String SELECT_ALL_JOBS = "select * from jobs";

    private static final String SELECT_ALL_JOBS_with_min_salary = "select * from jobs where min_salary = ?";
    private static final String SELECT_ALL_JOBS_min_PAGINATION = "select * from jobs min_salary = ? order by job_id limit ? offset ?";
    private static final String SELECT_ALL_JOBS_PAGINATION = "select * from jobs order by job_id limit ? offset ?";

    private static final String SELECT_ONE_JOB = "select * from jobs where job_id = ?";
    private static final String INSERT_JOB = "insert into jobs values (?, ?, ?, ?)";
    private static final String UPDATE_JOB = "update jobs set job_id = ?, job_title = ? where max_salary = ?";
    private static final String DELETE_JOB = "delete from jobs where job_id = ?";

    public void insertJob(Job j) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(INSERT_JOB);
        st.setInt(1, j.getJob_id());
        st.setString(2, j.getJob_title());
        st.setDouble(3, j.getMax_salary());
        st.setDouble(4, j.getMin_salary());
        st.executeUpdate();
    }

    public void updateJob(Job j) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(UPDATE_JOB);
        st.setInt(1, j.getJob_id());
        st.setString(2, j.getJob_title());
        st.setDouble(3, j.getMax_salary());
        st.executeUpdate();
    }

    public void deleteJob(int job_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(DELETE_JOB);
        st.setInt(1, job_id);
        st.executeUpdate();
    }

    public Job selectJob(int Job_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_JOB);
        st.setInt(1, Job_id);
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return new Job(rs);
        }
        else {
            return null;
        }
    }

//    public ArrayList<Job> selectAllJob(Double min_salary, Integer limit, Integer offset) throws SQLException, ClassNotFoundException {
//    Class.forName("org.sqlite.JDBC");
//    Connection conn = DriverManager.getConnection(URL);
//    PreparedStatement st;
//
//    if(min_salary != null && limit != null) {
//        st = conn.prepareStatement(SELECT_ALL_JOBS_min_PAGINATION);
//        st.setDouble(1, min_salary);
//        st.setInt(2, limit);
//        st.setInt(3, offset);
//    }
//    else if(min_salary != null){
//        st = conn.prepareStatement(SELECT_ALL_JOBS_with_min_salary);
//        st.setDouble(1, min_salary);
//    }
//    else if(limit != null){
//        st = conn.prepareStatement(SELECT_ALL_JOBS_PAGINATION);
//        st.setInt(1, limit);
//        st.setInt(2, offset);
//    }
//    else {
//        st = conn.prepareStatement(SELECT_ALL_JOBS);
//    }
//    ResultSet rs = st.executeQuery();
//    ArrayList<Job> job = new ArrayList<>();
//    while (rs.next()) {
//        job.add(new Job(rs));
//    }
//
//    return job;
//}

    public ArrayList<Job> selectAllJob(JobFilterDto filter) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st;

        if(filter.getMin_salary() != null && filter.getLimit() != null) {
            st = conn.prepareStatement(SELECT_ALL_JOBS_min_PAGINATION);
            st.setDouble(1, filter.getMin_salary());
            st.setInt(2, filter.getLimit());
            st.setInt(3, filter.getLimit());
        }
        else if(filter.getMin_salary() != null){
            st = conn.prepareStatement(SELECT_ALL_JOBS_with_min_salary);
            st.setDouble(1, filter.getMin_salary());
        }
        else if(filter.getLimit() != null){
            st = conn.prepareStatement(SELECT_ALL_JOBS_PAGINATION);
            st.setInt(1, filter.getLimit());
            st.setInt(2, filter.getOffset());
        }
        else {
            st = conn.prepareStatement(SELECT_ALL_JOBS);
        }
        ResultSet rs = st.executeQuery();
        ArrayList<Job> job = new ArrayList<>();
        while (rs.next()) {
            job.add(new Job(rs));
        }

        return job;
    }
}


