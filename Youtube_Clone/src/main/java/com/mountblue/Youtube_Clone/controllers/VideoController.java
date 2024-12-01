package com.mountblue.Youtube_Clone.controllers;

import com.mountblue.Youtube_Clone.entities.Video;
import com.mountblue.Youtube_Clone.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
public class VideoController
{
    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    // Endpoint to display the video upload form
    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload";
    }

    // Endpoint to handle video upload
    @PostMapping("/uploads")
    public String uploadVideo(@RequestParam("video") MultipartFile videoFile,
                              @RequestParam("thumbnail") MultipartFile thumbnailFile,
                              @RequestParam("title") String title,
                              @RequestParam("description") String description,
                              Principal principal) {
        videoService.saveVideo(videoFile, thumbnailFile, title, description, principal.getName());
        return "redirect:/list";
    }

    // Endpoint to list all videos
    @GetMapping("/list")
    public String listVideos(Model model)
    {
        List<Video> videos = videoService.getAllVideos();
        videos.forEach(video -> {
            System.out.println("Video URL: " + video.getVideoUrl());
            System.out.println("Thumbnail URL: " + video.getThumbnailUrl());
        });

        model.addAttribute("videos", videoService.getAllVideos());
        return "videos"; // Thymeleaf template to display the list of videos
    }

    @GetMapping("/image")
    public String show()
    {
        return "test";
    }

}
