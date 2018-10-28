package com.xiaohe.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 
 * @date 2014-2-14 下午1:24:30 
 * @author xly
 * @Description:
 * @project TJY
 */
public class FileUtils {
	public static void save(String path, String data) {
		try {
			File file = new File(path);
			File dir = new File(path.substring(0, path.lastIndexOf("/")));
			if(!dir.exists()) {
				dir.mkdirs();
			}
			if(data == null){
				if(!file.exists()) {
					file.mkdirs();
				}
			}else{
				FileWriter out = new FileWriter(file);
				out.write(data);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static List<File> save(String path,HttpServletRequest request,HttpServletResponse response)throws IllegalStateException, IOException{		
        //创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext()); 
        List<File> files = new ArrayList<File>();
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                int pre = (int) System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(!myFileName.trim().equals("")){  
                        //System.out.println(myFileName);   
                        //定义上传路径  
                        File localFile = new File(path+ myFileName);  
                        file.transferTo(localFile); 
                        files.add(localFile);
                    }  
                }  
                //记录上传该文件后的时间  
               // int finaltime = (int) System.currentTimeMillis();  
                //System.out.println(finaltime - pre);  
            }  
        }
        return files;
	}
	
	public static List<File> save(String path,HttpServletRequest request,String userName,HttpServletResponse response)throws IllegalStateException, IOException{		
        //创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext()); 
        List<File> files = new ArrayList<File>();
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                int pre = (int) System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(!myFileName.trim().equals("")){  
                       // System.out.println(myFileName);  
                        //重命名上传后的文件名  
                        String fileName = myFileName.substring(0, myFileName.lastIndexOf(".")) 
                        		+ "("+ userName + formatter.format(currentTime) + ")"+myFileName.substring(myFileName.lastIndexOf(".",myFileName.length()));  
                        //定义上传路径  
                        File localFile = new File(path+ fileName);  
                        file.transferTo(localFile); 
                        files.add(localFile);
                    }  
                }  
                //记录上传该文件后的时间  
                int finaltime = (int) System.currentTimeMillis();  
               // System.out.println(finaltime - pre);  
            }  
        }
        return files;
	}
	/**
	 * 下载文件时指定下载名
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param filePath
	 *            文件全路径
	 * @param fileName
	 *            指定客户端下载时显示的文件名
	 * @throws IOException
	 */
	public static void downloadFile(HttpServletRequest request,
	        HttpServletResponse response, String filePath, String fileName)
	        throws IOException {
	    BufferedInputStream bis = null;
	    BufferedOutputStream bos = null;
	 
	    bis = new BufferedInputStream(new FileInputStream(filePath));
	    bos = new BufferedOutputStream(response.getOutputStream());
	 
	    long fileLength = new File(filePath).length();
	    response.reset();
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("multipart/form-data");
	    /*
	     * 解决各浏览器的中文乱码问题
	     */
	    String userAgent = request.getHeader("User-Agent");
	    byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes()
	            : fileName.getBytes("UTF-8"); // fileName.getBytes("UTF-8")处理safari的乱码问题
	    fileName = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码
	    response.setHeader("Content-disposition",
	            String.format("attachment; filename=\"%s\"", fileName));	 
	    response.setHeader("Content-Length", String.valueOf(fileLength));
	    byte[] buff = new byte[2048];
	    int bytesRead;
	    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	        bos.write(buff, 0, bytesRead);
	    }
	    bis.close();
	    bos.close();
	 
	}
	
    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     */
    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
            	File file= new File(dir, children[i]);
	            deleteDir(file);
            }
        }
    }
}
