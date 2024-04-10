package fitConnect.restController;

import lombok.RequiredArgsConstructor;
import fitConnect.controller.dto.BoardRequestDto;
import fitConnect.controller.dto.response.BoardResponseDto;
import fitConnect.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest-api/boards")
public class BoardRestController {
    private final BoardService boardService;


    @GetMapping("/posts")
    public ResponseEntity<List<BoardResponseDto>> getPosts() {
        List<BoardResponseDto> posts = boardService.getPosts();
        return ResponseEntity.ok(posts);
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

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<Optional<List<BoardResponseDto>>> getPostByUserName(@PathVariable String userId) {
        Optional<List<BoardResponseDto>> posts = boardService.getPostsByUserId(userId);
        if (posts.isPresent()) {
            return ResponseEntity.ok(posts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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

