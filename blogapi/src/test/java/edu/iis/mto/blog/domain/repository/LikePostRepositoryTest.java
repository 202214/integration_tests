package edu.iis.mto.blog.domain.repository;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private LikePostRepository likePostRepository;
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired
    private BlogPostRepository blogPostRepository;
    
    private LikePost likePost;
    private BlogPost post;
    private User user;
    private User user2;
    
    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);
        user2 = new User();
        user2.setFirstName("Anna");
        user2.setEmail("anna@domain.com");
        user2.setAccountStatus(AccountStatus.NEW);
        post = new BlogPost();
        post.setUser(user);
        post.setEntry("test");
        likePost = new LikePost();
        likePost.setPost(post);
        likePost.setUser(user2);
    }
    
    @Test
    public void shouldFindNoLikePostsIfRepositoryIsEmpty() {
        List<LikePost> likePosts = likePostRepository.findAll();

        Assert.assertThat(likePosts, Matchers.hasSize(0));
    }
    
    @Test
    public void shouldFindUsersLikePost() {
        userRepository.save(user);
        userRepository.save(user2);
        blogPostRepository.save(post);
        likePostRepository.save(likePost);
        LikePost receivedLikePost = likePostRepository.findByUserAndPost(user2, post).get();
        Assert.assertThat(receivedLikePost.getUser(), Matchers.is(user2));
    }
    
}
