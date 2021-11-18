package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜잭션 시작

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();// 엔티티 프로젝션에서 나온 결과든은 영속성 컨텍스트가 관리를 해준다.

            MemberDTO memberDTO = resultList.get(0);

            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());


            /* Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class); // 타입정보를 명확하게 가지고있음.
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class); // 타입정보를 명확하게 가지고 있음.
            Query query3 = em.createQuery("select m.username, m.age from Member m"); // 타입정보를 명확하게 가지고 있지 않음.

            List<Member> resultList = query1.getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }*/

            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close(); // em 종료
        }
    }
}
