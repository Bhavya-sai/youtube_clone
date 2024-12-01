package com.mountblue.Youtube_Clone.services;

import com.mountblue.Youtube_Clone.entities.Users;
import com.mountblue.Youtube_Clone.entities.Video;
import com.mountblue.Youtube_Clone.repositories.UserRepository;
import com.mountblue.Youtube_Clone.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VideoService
{
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    // Directory to store video and thumbnail files
    private final String UPLOAD_DIR = "src/main/resources/static/images/";

    @Autowired
    public VideoService(VideoRepository videoRepository, UserRepository userRepository) {
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }



    public void saveVideo(MultipartFile videoFile, MultipartFile thumbnailFile,String title, String description, String username) {
        try {
            // Ensure upload directory exists
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Save video file
            String videoFilename = System.currentTimeMillis() + "_" + videoFile.getOriginalFilename();
            Path videoPath = Paths.get(UPLOAD_DIR + videoFilename);
            Files.write(videoPath, videoFile.getBytes());

            // Save thumbnail file
            String thumbnailFilename = System.currentTimeMillis() + "_" + thumbnailFile.getOriginalFilename();
            Path thumbnailPath = Paths.get(UPLOAD_DIR + thumbnailFilename);
            Files.write(thumbnailPath, thumbnailFile.getBytes());

            // Fetch the user who uploaded the video
            Users user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

            // Save video metadata to the database
            Video video = new Video(
                    title,
                    description,
                    "images/" + videoFilename,
                    "images/" + thumbnailFilename,
                    user,
                    null
            );
            video.setUploadedAt(LocalDateTime.now());

            videoRepository.save(video);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store files", e);
        }
    }
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

}
