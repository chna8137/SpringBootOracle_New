package kopo.poly.controller;

import kopo.poly.dto.WeatheryDTO;
import kopo.poly.service.IWeatheryService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping(value = "/weather")
@RequiredArgsConstructor
@RestController
public class WeatheryController {

    private final IWeatheryService weatheryService;

    @GetMapping(value = "getWeather")
    public WeatheryDTO getWeather(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getWeather Start!");

        String lat = CmmUtil.nvl(request.getParameter("lat"));
        String lon = CmmUtil.nvl(request.getParameter("lon"));

        WeatheryDTO pDTO = new WeatheryDTO();
        pDTO.setLat(lat);
        pDTO.setLon(lon);

        WeatheryDTO rDTO = weatheryService.getWeather(pDTO);

        if(rDTO == null) {
            rDTO = new WeatheryDTO();
        }

        log.info(this.getClass().getName() + ".getWeather End!");

        return rDTO;
    }


}
