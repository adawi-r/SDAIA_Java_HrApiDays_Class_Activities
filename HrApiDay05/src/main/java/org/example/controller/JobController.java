package org.example.controller;

import jakarta.ws.rs.*;
import org.example.DAO.JobDAO;
import org.example.models.Job;
import java.util.ArrayList;

@Path("/jobs")
public class JobController {

    JobDAO dao = new JobDAO();

    @GET
    public ArrayList<Job> selectAllJob() {

        try {
            return dao.selectAllJob();
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
