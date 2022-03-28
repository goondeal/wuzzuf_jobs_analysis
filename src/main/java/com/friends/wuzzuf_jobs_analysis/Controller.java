package com.friends.wuzzuf_jobs_analysis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;


@RestController
public class Controller {

    @Autowired
    Services services ;

    @RequestMapping("/summary")
    public Map<String, Object> shwoData(){
      return services.getSummary();
    }

    @RequestMapping("/popular_companies")
    public List<Map> popularCompanies(){
        return services.getMostPopularCompanies();
    }
    
    
    @RequestMapping("/popular_titles")
    public List<Map> popularTitles(){
        return services.getMostPopularJobTiltes();
    }

    @RequestMapping("/popular_areas")
    public List<Map> popularAreas(){
        return services.getMostPopularAreas();
    }

    @RequestMapping("/popular_skills")
    public List<Map> popularSkills(){
        return services.getMostPopularSkills();
    }


    @RequestMapping(value = "/top_20_companies", produces = IMAGE_JPEG_VALUE)
    public void top20Companies(HttpServletResponse responce) throws IOException {
        responce.setContentType(IMAGE_JPEG_VALUE);
        String path = services.getTop20CompaniesChartPath();
        if (path != null) {
        	ClassPathResource imgFile = new ClassPathResource(path);
            StreamUtils.copy(imgFile.getInputStream(), responce.getOutputStream());
        }
    }

    @RequestMapping(value = "/top_20_titles", produces = IMAGE_JPEG_VALUE)
    public void popular_title_char(HttpServletResponse responce) throws IOException {
        responce.setContentType(IMAGE_JPEG_VALUE);
        String path = services.getTop20TitlesChartPath();
        if (path != null) {
        	ClassPathResource imgFile = new ClassPathResource(path);
            StreamUtils.copy(imgFile.getInputStream(), responce.getOutputStream());
        }
    }

    @RequestMapping(value = "/top_50_areas", produces = IMAGE_JPEG_VALUE)
    public void popular_area_char(HttpServletResponse responce) throws IOException {
        responce.setContentType(IMAGE_JPEG_VALUE);
        String path = services.getTop50AreasChartPath();
        if (path != null) {
        	ClassPathResource imgFile = new ClassPathResource(path);
            StreamUtils.copy(imgFile.getInputStream(), responce.getOutputStream());
        }
    }

}