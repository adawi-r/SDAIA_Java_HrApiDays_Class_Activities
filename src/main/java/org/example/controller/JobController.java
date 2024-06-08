package org.example.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.example.DAO.JobDAO;
import org.example.dto.JobDto;
import org.example.dto.JobFilterDto;
import org.example.exceptions.DataNotFoundException;
import org.example.models.Job;

import java.sql.SQLException;
import java.util.ArrayList;
import java.net.URI;


@Path("/jobs")
public class JobController {

    JobDAO dao = new JobDAO();
    @Context UriInfo uriInfo;
    @Context HttpHeaders headers;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response selectAllJob(
              @BeanParam JobFilterDto filter
            ) {
        try {
            GenericEntity<ArrayList<Job>> job = new GenericEntity<ArrayList<Job>>(dao.selectAllJob(filter)) {
            };
            if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(job)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            }
            return Response
//                    .ok(job)
//                    .type(MediaType.APPLICATION_JSON)
                    .ok(job, MediaType.APPLICATION_JSON)
                    .build();

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("{jobId}")
    public Response selectJob(@PathParam("jobId") int jobId) throws SQLException{

        try {
            Job job = dao.selectJob(jobId);
            if (job == null) {
                throw new DataNotFoundException("Job " + jobId + "Not found");
            }

            JobDto dto = new JobDto();
            dto.setJob_id(job.getJob_id());
            dto.setJob_title(job.getJob_title());
            dto.setMin_salary(job.getMin_salary());
            dto.setMax_salary(job.getMax_salary());
            addLinks(dto);

            return Response.ok(dto).build();
        }
        catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addLinks (JobDto dto){
        URI selfUri = uriInfo.getAbsolutePath();
        URI empsUri = uriInfo.getAbsolutePathBuilder().path(JobController.class).build();

        dto.addLink(selfUri.toString(), "self");
        dto.addLink(empsUri.toString(),"employees");
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
    public Response insertJob(Job job){

        try {
            dao.insertJob(job);
            NewCookie cookie = (new NewCookie.Builder("username")).value("OOOOO").build();
            URI uri = uriInfo.getAbsolutePathBuilder().path(job.getJob_id() + "").build();

            return Response
//                    .status(Response.Status.CREATED)
                    .created(uri)
//                    .cookie(new NewCookie("username", "OOOOO"))
                    .cookie(cookie)
                    .header("Created by", "Raghad")
                    .build();

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    public Response insertJobFromForm(@BeanParam Job job){

        try {
            dao.insertJob(job);
            NewCookie cookie = (new NewCookie.Builder("username")).value("OOOOO").build();
            URI uri = uriInfo.getAbsolutePathBuilder().path(job.getJob_id() + "").build();

            return Response
//                    .status(Response.Status.CREATED)
                    .created(uri)
//                    .cookie(new NewCookie("username", "OOOOO"))
                    .cookie(cookie)
                    .header("Created by", "Raghad")
                    .build();

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
