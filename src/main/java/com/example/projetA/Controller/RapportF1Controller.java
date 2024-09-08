package com.example.projetA.Controller;

import com.example.projetA.Entity.RapportF1;
import com.example.projetA.Repository.RapportF1Repository;
import com.example.projetA.Services.RapportF1Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class RapportF1Controller {

    @Autowired
    private RapportF1Service rapportF1Service;

    @Autowired
    private RapportF1Repository rapportF1Repository;

    @PostMapping("/upload")
    public RapportF1 upload(@RequestParam("file") MultipartFile file) throws Exception {
        return rapportF1Service.uploadFile(file);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("id") Long id) {
        Optional<RapportF1> rapport = rapportF1Repository.findByidRapport(id);
        if(rapport.isPresent()){
            RapportF1 rapportF1 = rapport.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(rapportF1.getFiletype()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + rapportF1.getFilename() + "\"")
                    .body(new ByteArrayResource(rapportF1.getContenu()));
        }
        else {
            throw new RuntimeException ("Appareil  n'a pas ete trouvé");
        }



    }


}
