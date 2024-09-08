package com.example.projetA.Services;

import com.example.projetA.Entity.RapportF1;
import org.springframework.web.multipart.MultipartFile;

public interface RapportF1Service {
    public RapportF1 uploadFile(MultipartFile file) throws Exception;
}
