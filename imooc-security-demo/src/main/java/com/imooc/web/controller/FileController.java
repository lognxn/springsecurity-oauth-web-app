package com.imooc.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.imooc.dto.FileInfo;

/**
 * 文件上传的controller
 * 
 * @author longxn
 *
 */
@RestController
@RequestMapping("/file")
public class FileController {
	private String folder = "C:/vtx/workspace/imooc-security-demo/src/main/java/com/imooc/web/controller";

	/**
	 * 
	 * @param file
	 *            传入的file对象, 参考测试用例中的whenUploadSuccess方法
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@PostMapping
	public FileInfo upload(MultipartFile file) throws Exception {
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());

		File localFile = new File(folder, new Date().getTime() + ".txt");
		file.transferTo(localFile);

		return new FileInfo(localFile.getAbsolutePath());
	}

	@GetMapping("/{id}")
	public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try (InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
				OutputStream outputStream = response.getOutputStream()) {
			response.setContentType("application/x-download");
			//filename:重新设置下载后的文件名
			response.addHeader("Content-Disposition", "attachment;filename=text.txt");
			IOUtils.copy(inputStream, outputStream);
			outputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
