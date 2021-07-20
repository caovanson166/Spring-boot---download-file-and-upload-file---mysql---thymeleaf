package com.demo;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class DocumentController {
	@Autowired
	private DocumentRepository repo;
	
	@GetMapping("/")
	public String ViewHomePage(Model model) {
		List<entity> list=repo.findAll();
		model.addAttribute("list", list);
		return "index";
	}
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("documents") MultipartFile File,
			RedirectAttributes ra ) throws IOException {
		String FileName=StringUtils.cleanPath(File.getOriginalFilename());
		entity abc =new entity();
		abc.setName(FileName);
		abc.setContent(File.getBytes());
		abc.setSize(File.getSize());
		abc.setUploadTime(new Date());
		repo.save(abc);
		ra.addFlashAttribute("message", "The file has been uploaded successfully .");
		return "redirect:/";
	}
	@GetMapping("/download")
	public void Download(@Param("id") Integer id,HttpServletResponse res) throws Exception {
		Optional<entity> a=repo.findById(id);
		if(!a.isPresent()) {
			throw new Exception("no................");
		}
		entity document=a.get();
		res.setContentType("application/octet-stream");
		String headerKey="Content-Disposition";
		String headerValue="attachment; filename = "+document.getName();
		res.setHeader(headerKey, headerValue);
		ServletOutputStream o =res.getOutputStream();
		o.write(document.getContent());
		o.close();
	}
}
