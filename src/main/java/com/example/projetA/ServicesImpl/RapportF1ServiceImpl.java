package com.example.projetA.ServicesImpl;

import com.example.projetA.Entity.RapportF1;
import com.example.projetA.Repository.RapportF1Repository;
import com.example.projetA.Services.RapportF1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


@Service
@Transactional
public class RapportF1ServiceImpl implements RapportF1Service {

    @Autowired
    private RapportF1Repository rapportF1Repository;

    //@Autowired
   // private Path fileStorageLocation ;

   // @Override

   // public String uploadFile(MultipartFile file){
       // String fileName = file.getOriginalFilename();

       // try {
          //  if (fileName.contains("..")) {
           //     throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
           //}

           // Path targetLocation = this.fileStorageLocation.resolve(fileName);
            //Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

          //  RapportF1 rapportF1 = new RapportF1(1L,fileName, file.getContentType(), targetLocation.toString(), file.getSize() );
           // rapportF1Repository.save(rapportF1);

           // return fileName;
        //} catch (IOException ex) {
          //  throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
      //  }

   // }

     @Override
     public RapportF1 uploadFile(MultipartFile file)  {
         String filename = file.getOriginalFilename();
         System.out.println(filename);

         try {
             if(filename.contains("..")) {
                 throw new IllegalArgumentException("le nom du fichier est invalide");
             }

             RapportF1 rapportF1 = new RapportF1();
             rapportF1.setFilename(filename);
             rapportF1.setFiletype(file.getContentType());

             rapportF1.setContenu(file.getBytes());
             System.out.println(rapportF1);
             rapportF1= rapportF1Repository.save(rapportF1);
             System.out.println(rapportF1);
            return rapportF1;


         } catch (Exception e) {
             throw new RuntimeException("Erreur lors de la sauvegarde du fichier : " + e.getMessage(), e);
         }

     }
}
