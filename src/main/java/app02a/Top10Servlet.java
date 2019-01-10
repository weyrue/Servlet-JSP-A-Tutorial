package app02a;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "Top10Servlet", urlPatterns = "/top10")
public class Top10Servlet extends HttpServlet {

    private List<String> londonAttractions;
    private List<String> parisAttractions;

    @Override
    public void init() throws ServletException {
        londonAttractions = new ArrayList<>(10);
        londonAttractions.add("Buckingham Palace");
        londonAttractions.add("London Eye");
        londonAttractions.add("British Museum");
        londonAttractions.add("National Gallery");
        londonAttractions.add("Big Ben");
        londonAttractions.add("Tower of London");
        londonAttractions.add("Natural History Museum");
        londonAttractions.add("Canary Wharf");
        londonAttractions.add("2012 Olympic Park");
        londonAttractions.add("St Paul's Cathedral");

        parisAttractions = new ArrayList<>(10);
        parisAttractions.add("Eiffel Tower");
        parisAttractions.add("Norte Dame");
        parisAttractions.add("The Lourve");
        parisAttractions.add("Champs Elysees");
        parisAttractions.add("Arc de Triomphe");
        parisAttractions.add("Sainte Chapelle Church");
        parisAttractions.add("Les Invalides");
        parisAttractions.add("Musee d'Orsay");
        parisAttractions.add("Montmarte");
        parisAttractions.add("Sacre Couer Basilica");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String city = req.getParameter("city");
        if (city != null && (city.equals("london") || city.equals("paris"))) {
            showAttractions(req, resp, city);
        } else {
            showMainPage(req, resp);
        }
    }

    private void showMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.print("<html><head>" +
                "<title>Top 10 Tourist Attractions</title>" +
                "</head><body>" +
                "Please select a city:" +
                "<br/><a href='?city=london'>London</a>" +
                "<br/><a href='?city=paris'>Paris</a>" +
                "</body></html>");
    }

    private void showAttractions(HttpServletRequest request, HttpServletResponse response, String city) throws ServletException, IOException {
        int page = 1;
        String pageParameter = request.getParameter("page");
        if (pageParameter != null) {
            try {
                page = Integer.parseInt(pageParameter);
            } catch (NumberFormatException e) {
            }
            if (page > 2) {
                page = 1;
            }
        }

        List<String> attractions = null;

        if (city.equals("london")) {
            attractions = londonAttractions;
        } else if (city.equals("paris")) {
            attractions = parisAttractions;
        }

        response.setContentType("text/html");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html><head>" +
                "<title>Top 10 Tourist Attractions</title>" +
                "</head><body>" +
                "<a href='top10'>Select City</a>" +
                "<hr/>Page " + page +
                "<hr/>");
        int start = page * 5 - 5;

        for (int i = start; i < start + 5; i++) {
            writer.print(attractions.get(i) + "<br/>");
        }
        writer.println("<hr style='color:blue'/>" +
                "<a href='?city=" + city + "&page=1'>Page 1</a>" +
                "&nbsp;<a href='?city=" + city + "&page=2'>Page 2</a>" +
                "</body></html>");
    }

}
