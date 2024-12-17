package fitConnect.restController;

import fitConnect.dto.BoardRequestDto;
import fitConnect.dto.response.BoardPageResponseDto;
import fitConnect.dto.response.BoardResponseDto;
import fitConnect.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest-api/boards")
public class BoardRestController {
    private final BoardService boardService;
//    @GetMapping("/posts")
//    public ResponseEntity<List<BoardResponseDto>> getPosts() {
//        List<BoardResponseDto> posts = boardService.getPosts();
//        return ResponseEntity.ok(posts);
//    }

    @GetMapping("/posts")
    public ResponseEntity<BoardPageResponseDto> getPosts(@RequestParam(defaultValue = "0")int page,
                                                                  @RequestParam(defaultValue = "10")int size)
    {
        BoardPageResponseDto pageInfo=boardService.getPosts(page,size);
        return new ResponseEntity<>(pageInfo,HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody BoardRequestDto requestDto, Authentication authentication) {
        try {
            BoardResponseDto post=boardService.getPost(boardService.createPost(requestDto, authentication).getPostNum());
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        try{
            BoardResponseDto post = boardService.getPost(id);
            return ResponseEntity.ok(post);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/users/{userName}/posts")
    public ResponseEntity<Optional<List<BoardResponseDto>>> getPostByUserName(@PathVariable("userName") String userName) {
        Optional<List<BoardResponseDto>> posts = boardService.getPostsByUserName(userName);
        if (posts.isPresent()) {
            return ResponseEntity.ok(posts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("@boardService.isAuthor(#postNum,authentication.name)")
    @PutMapping("/post/{postNum}")
    public ResponseEntity<?> updatePost(@PathVariable Long postNum, Authentication authentication, @RequestBody BoardRequestDto requestDto) {
        try {
            boardService.updatePost(postNum, authentication, requestDto);
            BoardResponseDto post=boardService.getPost(postNum);
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("@boardService.isAuthor(#postNum,authentication.name)")
    @DeleteMapping("/post/{postNum}")
    public ResponseEntity<?> deletePost(@PathVariable Long postNum, Authentication authentication) {
        try {
            BoardResponseDto post=boardService.getPost(postNum);
            boardService.deletePost(postNum, authentication);
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

