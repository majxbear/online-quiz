package cn.kgc.quiz.res;

import cn.kgc.quiz.message.SimpleMsg;
import cn.kgc.quiz.message.UploadMsg;
import com.alibaba.fastjson.JSON;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.UUID;

@Path("file")
public class FileRes {

    private static final String FILE_PATH = "D:/file/upload/";

    /**
     * file upload
     *
     * @param inputStream
     * @param disposition
     * @return
     */
    @Path("upload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@FormDataParam("file") InputStream inputStream,
                           @FormDataParam("file") FormDataContentDisposition disposition) {
        String fileName = UUID.randomUUID().toString();
        String originName;
        originName = disposition.getFileName();
        if (originName.contains("."))
            fileName = fileName +
                    originName.substring(originName.lastIndexOf("."), originName.length());

        try {
            writeToFileServer(inputStream, fileName);
            return Response.status(Response.Status.OK)
                    .entity(JSON.toJSONString(new UploadMsg(fileName)))
                    .build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(JSON.toJSONString(new SimpleMsg(e.getMessage())))
                    .build();
        }
    }

    /**
     * file download
     *
     * @param name file name
     * @return
     */
    @Path("download/{name}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("name") String name) {
        String fileName = FILE_PATH + name;
        File file = new File(fileName);
        if (!file.exists()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(JSON.toJSONString(new SimpleMsg("file not found")))
                    .build();
        }
        Response.ResponseBuilder rb = Response.ok(file);
        rb.header("Content-Disposition", "attachment; filename=" + fileName);
        return rb.build();
    }

    private String writeToFileServer(InputStream inputStream, String fileName) throws IOException {
        OutputStream outputStream = null;
        File file = new File(FILE_PATH);
        if (!file.exists())
            file.mkdirs();
        String qualifiedUploadFilePath = FILE_PATH + fileName;
        try {
            outputStream = new FileOutputStream(new File(qualifiedUploadFilePath));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (outputStream != null) outputStream.close();
        }
        return qualifiedUploadFilePath;
    }

}
