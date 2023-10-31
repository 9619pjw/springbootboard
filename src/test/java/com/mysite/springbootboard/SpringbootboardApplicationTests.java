package com.mysite.springbootboard;

import com.mysite.springbootboard.repository.CommentRepository;
import com.mysite.springbootboard.domain.Post;
import com.mysite.springbootboard.repository.PostRepository;
import com.mysite.springbootboard.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SpringbootboardApplicationTests {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostService postService;

	@Test
	void testJpa() {
		/*
		// 게시글 생성하는 테스트 코드
		Post p1 = new Post();
		p1.setSubject("Springboard가 무엇인가요?");
		p1.setContent("Spring에 대해 알고 싶습니다.");
		p1.setCreateDate(LocalDateTime.now());

		this.postRepository.save(p1); // 첫번째 게시글 저장

		Post p2 = new Post();
		p2.setSubject("스프링 부트 모델 질문입니다.");
		p2.setContent("id는 자동으로 생성되나요?");
		p2.setCreateDate(LocalDateTime.now());

		this.postRepository.save(p2); // 두번째 게시글 저장
		*/

		// Post 테이블에 저장된 모든 데이터를 조회
		/*
		List<Post> all = this.postRepository.findAll();
		assertEquals(2, all.size());

		Post p = all.get(0);
		assertEquals("Springbootboard가 무엇인가요?", p.getSubject());
		*/

		// Post 엔티티의 Id값으로 데이터 조회하는 테스트
		/*
		Optional<Post> op = this.postRepository.findById(1);
		if(op.isPresent()) {
			Post p = op.get();
			assertEquals("Springbootboard가 무엇인가요?", p.getSubject());
		}
		*/

		// Post 엔티티의 subject값으로 데이터를 조회
		/*
		Post p = this.postRepository.findBySubject("Springbootboard가 무엇인가요?");
		assertEquals(1, p.getId());
		*/


		// Post 엔티티의 subject값, content값으로 데이터를 조회
		/*
		Post p = this.postRepository.findBySubjectAndContent("Springbootboard가 무엇인가요?", "Springbootboard에 대해 알고 싶습니다.");
		assertEquals(1, p.getId());
		*/

		// Post 엔티티의 subject에 특정 문자열이 포함되어있는 것으로 데이터를 조회함.
		/*
		List<Post> pList = this.postRepository.findBySubjectLike("spring%");
		Post p = pList.get(0);
		assertEquals("Springbootboard가 무엇인가요?", p.getSubject());
		*/

		// Post 데이터를 수정하는 테스트 코드
		/*
		Optional<Post> op = this.postRepository.findById(1);
		assertTrue(op.isPresent());
		Post p = op.get();
		p.setSubject("수정된 제목");
		this.postRepository.save(p);
		*/

		// Post 데이터를 삭제하는 테스트 코드
		/*
		assertEquals(2, this.postRepository.count());
		Optional<Post> op = this.postRepository.findById(1);
		assertTrue(op.isPresent());
		Post p = op.get();
		this.postRepository.delete(p);
		assertEquals(1, this.postRepository.count());
		*/

		// Comment 데이터 생성 후 저장하는 TC
		/*
		Optional<Post> op = this.postRepository.findById(2);
		assertTrue(op.isPresent());
		Post p = op.get();

		Comment c = new Comment();
		c.setContent("네 자동으로 생성됩니다.");
		c.setPost(p); // 어떤 게시글의 댓글인지 알기 위해서 Post 객체가 필요함.
		c.setCreateDate(LocalDateTime.now());
		this.commentRepository.save(c);
		*/

		// 댓글 조회하는 TC
		/*
		Optional<Comment> oc = this.commentRepository.findById(1);
		assertTrue(oc.isPresent());
		Comment c = oc.get();
		assertEquals(2, c.getPost().getId());
		*/

		// Post 객체로부터 댓글 리스트를 구하는 테스트 코드
		/** 테스트 코드 한정으로
		 * findById() 호출한 뒤 Post 객체 조회하고 나면 DB 세션이 끊어져 오류 발생
		 * 테스트 코드에서도 돌아가기 위해서 @Transactional 어노테이션을 사용
		 */
		/*
		Optional<Post> op = this.postRepository.findById(2);
		assertTrue(op.isPresent());
		Post p = op.get();

		List<Comment> commentList = p.getCommentList();

		assertEquals(1, commentList.size());
		assertEquals("네 자동으로 생성됩니다.", commentList.get(0).getContent());
		*/

		/*
		// 대량의 테스트 데이터 생성
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용 무";
			this.postService.create(subject, content, null);
		}
		 */

		// 공지글 데이터 생성
		String subject = "공지사항입니다.";
		String content = "공지사항 내용입니다.";
		Boolean isNotice = true; // 공지사항임을 표시

		Post noticePost = this.postService.create(subject, content, null, isNotice);

		assertNotNull(noticePost); // 저장 후 반환된 객체가 null이 아닌지 검증
		assertEquals(subject, noticePost.getSubject()); // 제목 검증
		assertEquals(content, noticePost.getContent()); // 내용 검증
		assertTrue(noticePost.getIsNotice()); // 공지사항 여부 검증
	}

}
