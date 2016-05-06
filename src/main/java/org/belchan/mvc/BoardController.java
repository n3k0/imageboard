package org.belchan.mvc;

import org.belchan.model.Board;
import org.belchan.model.Post;
import org.belchan.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class BoardController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BoardService boardService;

    @RequestMapping(
            value = {"{boardName}/res/{numThread}.html"},
            method = {RequestMethod.GET}
    )
    public ModelAndView getBoard(Model model, @PathVariable("boardName") String boardName, @PathVariable("numThread") int numThread) {
        Board b = this.boardService.getBoard(boardName);
        model.addAttribute("board", b);
        List bs = this.boardService.getBoards();
        model.addAttribute("boards", bs);
        List ps = this.boardService.getThreadPosts(b, numThread);
        model.addAttribute("posts", ps);

        for (Object p1 : ps) {
            Post p = (Post) p1;
            this.log.info(p.toString());
        }

        return new ModelAndView("thread-view",model.asMap());
    }

    @RequestMapping(
            value = {"archive", "b", "bb", "bc", "bo", "by", "dt", "files", "fm", "int", "mu", "news", "t", "v", "vg", "wp",},
            method = {RequestMethod.GET}
    )
    public @ResponseBody String getBoard(Model model, HttpServletRequest request, HttpServletResponse response) {
        String boardName = request.getServletPath().split("/")[1];
        Board board = boardService.getBoard(boardName);
        if (board != null)
        {
            setStatus(response,boardName,"0","0");
            return "board.html";
        } else {
            return "404.html";
        }

    }

    private void setStatus(HttpServletResponse response, String board, String thread, String page) {
        response.addCookie(new Cookie("board",board));
        response.addCookie(new Cookie("thread",thread));
        response.addCookie(new Cookie("page",page));
    }



    @RequestMapping(
            value = {"boards"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public List<Board> getBoards() {
        return this.boardService.getBoards();
    }
}
/*







    @RequestMapping(
            value = {"board/{id}"},
            method = {RequestMethod.GET}
    )

    @ResponseBody
    public Board getBoard(@PathVariable("id") int id) {
        return this.boardService.getBoard(id);
    }

    */

//
//
//
//        if ((boardName == null) || (boardName.isEmpty()) || boardName.equalsIgnoreCase("index")) {
//            List b = this.boardService.getBoards();
//            model.addAttribute("boards", b);
//            return new ModelAndView("board-index",model.asMap());
//        }
//        Board b = this.boardService.getBoard(boardName);
//        model.addAttribute("board", b);
//        List bs = this.boardService.getBoards();
//        model.addAttribute("boards", bs);
//        List ps = this.boardService.getPagePosts(b, 0);
//        model.addAttribute("posts", ps);
//
//
//        for (Object p1 : ps) {
//            Post p = (Post) p1;
//            this.log.info(p.toString());
//        }
//
//        return new ModelAndView("board-view");