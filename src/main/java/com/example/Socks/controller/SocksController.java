package com.example.Socks.controller;


import com.example.Socks.model.Operations;
import com.example.Socks.model.Socks;
import com.example.Socks.repository.SocksRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/socks")
@RestController
public class SocksController {
    private final SocksRepository socksRepository;

    public SocksController(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }


    @PostMapping("/income")
    @ResponseStatus(HttpStatus.OK)
    public Socks addSocks(@RequestBody Socks socks) {
        if (ObjectUtils.isEmpty(socks.getColor()) || ObjectUtils.isEmpty(socks.getCottonPart()) || ObjectUtils.isEmpty(socks.getQuantity())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        try {
            return socksRepository.save(socks);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/outcome")
    @ResponseStatus(HttpStatus.OK)
    public Socks removeSocks(@RequestBody Socks socks) {
        if (ObjectUtils.isEmpty(socks.getColor()) || ObjectUtils.isEmpty(socks.getCottonPart()) || ObjectUtils.isEmpty(socks.getQuantity())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        try {
            return socksRepository.save(socks);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String getSocksCountByParam(@RequestParam String color, @RequestParam Operations operation, @RequestParam Integer cottonPart) {
        if (color.isEmpty() || ObjectUtils.isEmpty(operation) || ObjectUtils.isEmpty(cottonPart)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        try {

            List<Socks> socksFound = new ArrayList<>();
            int count = 0;
            if (operation == Operations.equal) {
                socksFound = socksRepository.findByColorAndCottonPartEquals(color, cottonPart);
            } else if (operation == Operations.moreThan) {
                socksFound = socksRepository.findByColorAndCottonPartGreaterThan(color, cottonPart);
            } else if (operation == Operations.lessThan) {
                socksFound = socksRepository.findByColorAndCottonPartLessThan(color, cottonPart);
            }

            for (Socks socks : socksFound) {
                count += socks.getQuantity();
            }
            return String.valueOf(count);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public String getSocksCount() {
        return String.valueOf(socksRepository.count());
    }

}
