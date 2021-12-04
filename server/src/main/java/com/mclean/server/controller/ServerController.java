package com.mclean.server.controller;


import com.mclean.server.enumeration.Status;
import com.mclean.server.model.Response;
import com.mclean.server.model.Server;
import com.mclean.server.service.ServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {
    private final ServiceImplementation service;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Servers", service.list(30)))
                        .message("Servers Retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = service.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Server", server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping Success" : "Ping fail")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    //@valid will check for the validation we added in Server
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Server", service.create(server)))
                        .message("Server created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Server", service.get(id)))
                        .message("Server Retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Deleted", service.delete(id)))
                        .message("Server Deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] deleteServer(@PathVariable("fileName") String fileName) throws IOException{
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "IdeaProjects/server-manager/assets" + fileName)
        );
    }


}
