package com.mountblue.Youtube_Clone.repositories;

import com.mountblue.Youtube_Clone.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>
{

}
