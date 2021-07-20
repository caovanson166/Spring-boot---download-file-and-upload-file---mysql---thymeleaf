package com.demo.image;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.JpaSort.Path;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class controller {
	@Autowired
	private ImageRepository repo;
	
	@PostMapping
	public String saveImg(@ModelAttribute(name="img") img img,
			RedirectAttributes ra,
			@RequestParam("image") MultipartFile multipartFile
			) {
	String name=StringUtils.cleanPath(multipartFile.getOriginalFilename());
	img.setImg(name);
	img saveImg =repo.save(img);
	String upload="/logos/"+saveImg.getId();
	Path uploadPath =(Path) Paths.get(upload);
	if(!Files.exists(uploadPath)) {
		Files.createDirectories(null,null);
	}
	ra.addFlashAttribute("mess","successfully.");
	return "redirect:/";
	}
}
