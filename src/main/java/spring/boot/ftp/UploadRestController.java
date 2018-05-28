package spring.boot.ftp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static java.lang.String.format;

@RestController
public class UploadRestController {

    @Autowired
    private SftpConfig.UploadGateway gateway;

    @PostMapping
    public ResponseEntity<?> receiveRemoteFile(@RequestParam("file") MultipartFile file)  {
        String filename = file.getOriginalFilename();
        try {
            gateway.upload(filename, file.getBytes());
        } catch (Throwable e) {
            return ResponseEntity.status(500).body(format("Error uploading %s", filename));
        }
        return ResponseEntity.ok(format("File %s uploaded", filename));

    }
}
