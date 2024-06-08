package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.DAO.JobDAO;
import org.example.dto.JobFilterDto;
import org.example.models.Job;
import java.util.ArrayList;

@Path("/jobs")
public class JobController {

    JobDAO dao = new JobDAO();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ArrayList<Job> selectAllJob(
//            @QueryParam("min_salary") Double min_salary,
//            @QueryParam("limit") Integer limit,
//            @QueryParam("offset") int offset
              @BeanParam JobFilterDto filter
            ) {
        try {
//            return dao.selectAllJob(min_salary, limit, offset);
            return dao.selectAllJob(filter);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("{jobId}")
    public Job selectJob(@PathParam("jobId") int jobId){

        try {
            return dao.selectJob(jobId);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DELETE
    @Path("{jobId}")
    public void deleteJob(@PathParam("jobId") int jobId){

        try {
            dao.deleteJob(jobId);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @POST
//    @Consumes(MediaType.APPLICATION_XML)
    public void insertJob(Job job){

        try {
            dao.insertJob(job);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PUT
    @Path("{jobId}")
    public void updateJob(@PathParam("jobId") int jobId, Job job){

        try {
            job.setJob_id(jobId);
            dao.updateJob(job);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
