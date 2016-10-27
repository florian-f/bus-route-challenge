package routes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
public class RouteController {

    @Autowired
    Map<Integer, Set<Integer>> routes;

    @RequestMapping("/api/direct")
    public Response direct(@RequestParam("dep_sid") int from, @RequestParam("arr_sid") int to) {
        Response response = new Response(from, to, false);
        if (!routes.containsKey(from) || !routes.containsKey(to)) {
            return response;
        }

        Set<Integer> linesFrom = routes.get(from);
        Set<Integer> linesTo = routes.get(to);

        if (linesFrom == null || linesFrom.isEmpty() || linesTo == null || linesTo.isEmpty()) {
            return response;
        }

        boolean direct = linesFrom.size() < linesTo.size() ? directLine(linesFrom, linesTo) : directLine(linesTo, linesFrom);
        response.setDirect_bus_route(direct);

        return response;
    }

    private boolean directLine(Set<Integer> linesFrom, Set<Integer> linesTo) {
        if (linesFrom.size() < linesTo.size()) {
            for (Integer line : linesFrom) {
                if (linesTo.contains(line)) {
                    return true;
                }
            }
        }
        return false;
    }
}
