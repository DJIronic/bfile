package cz.vutbr.feec.utko.bpcmds.projekt;

import org.jcodec.api.JCodecException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class WebController {

    @Autowired
    private ProjectResourceComponent handler;

    private final static String DASH_DIRECTORY = "C:\\Users\\ondra\\Videos\\MPEG-DASH\\";
    private final static String SUFFIX = "mpd";

    ArrayList<Video> seznamVidei = new ArrayList<Video>();

    @RequestMapping(value = {"/dash/{file}"}, method = RequestMethod.GET)
    public void streaming(@PathVariable("file") String file, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        File STREAM_FILE = null;
            STREAM_FILE = new File(DASH_DIRECTORY + file);
         // for play big bunny "DASH-BIG_BUCK_BUNNY\\" in path
        request.setAttribute(ProjectResourceComponent.ATTR_FILE, STREAM_FILE);
        handler.handleRequest(request, response);
    }

    @GetMapping(value = {"/addvideo"})
    public String addVideo(){
        return "addvideo";
    }

    @RequestMapping(value="/videolibrary", method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView videolibrary(@RequestParam(required = false) String url,@RequestParam(required = false) String name, HttpServletRequest request){
        Map<String, Object> model = new HashMap<String, Object>();
        String viewName = "videolibrary";
        model.put("seznamVidei", seznamVidei);

        if(request.getMethod().equals("POST"))
        {
            if(url != "" & name != "")
            {seznamVidei.add(new Video(url,name));}
        }

        if(request.getMethod().equals("GET"))
        {
            url = "d";
            name = "d";

        }
        model.put("NoUrl", url);
        model.put("NoName", name);
        return new ModelAndView(viewName, model);
    }

    @RequestMapping(value = {"/player/{name}"}, method = RequestMethod.GET)
    public ModelAndView player(@PathVariable("name") String name) throws IOException {
        Map<String, Object> model = new HashMap<String, Object>();
        String viewName = "player";
        String url="";
        for (Video video:seznamVidei){
            if (video.getName().equals(name)){
                System.out.println(video.getName() + " == " + name);
                url=video.getURL();
                System.out.println(video.getURL());
            }
        }
        System.out.println(url);
        model.put("url", url);
        return new ModelAndView(viewName, model);
    }
}