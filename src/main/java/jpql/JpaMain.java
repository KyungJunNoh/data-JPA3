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
            // 벌크 연산 ( 자바 ORM 표준 JPA 프로그래밍 - 기본편 [완강] )
            Team teamA = new Team();
            teamA.setName("팀 A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀 B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);


            int resultCount = em.createQuery("update Member m set m.age = 20") // 모든 회원의 나이를 20살로 Update
                    .executeUpdate();
            System.out.println("resultCount = " + resultCount);

            em.clear(); // 클리어를 해주지 않으면 영속성 컨텍스트 안에 있는 멤버를 가져와서, 업데이트가 된 값이 아닌 나이가 0살로 가져와 버리는 상황이 연출됨.

            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember.getAge() = " + findMember.getAge()); // 결과 : 20


//            List<Member> resultList = em.createQuery("select m from Member m", Member.class) //
//                    .getResultList();
//
//
//            for (Member member : resultList) {
//                System.out.println("member.getUsername() = " + member.getUsername() + ", " + "member.getAge() = " + member.getAge() );
//            }

            // Named 쿼리
            /* Team teamA = new Team();
            teamA.setName("팀 A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀 B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "회원1")
                    .getResultList();

            for (Member member : resultList) {
                System.out.println("member = " + member);
            }*/

            // 엔티티 직접 사용 예제
            /* Team teamA = new Team();
            teamA.setName("팀 A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀 B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m From Member m where m.team = :team";
            List<Member> members = em.createQuery(query, Member.class)
                    .setParameter("team", teamA)
                    .getResultList();

            for (Member member : members) {
                System.out.println("member = " + member);
            }*/

            // 페치 조인 2 - 한계
            /* Team teamA = new Team();
            teamA.setName("팀 A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀 B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select t From Team t";

            List<Team> resultList = em.createQuery(query, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            System.out.println("resultList = " + resultList.size());

            for (Team team : resultList) {
                System.out.println("team = " + team.getName() + ", ");
                for (Member member : team.getMembers()) {
                    System.out.println("member = " + member);
                }
            };*/

            // 페치 조인 - 기본
            /* Team teamA = new Team();
            teamA.setName("팀 A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀 B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select distinct t From Team t join fetch t.members"; // 실무에서 묵시적 조인은 치명적이다. 명시적 조인 ( 직접 조인 해주는것 ) 을 습관화 하자

            List<Team> resultList = em.createQuery(query, Team.class).getResultList();

            System.out.println("resultList = " + resultList.size());

            for (Team team : resultList) {
                System.out.println("team = " + team.getName() + ", ");
                for (Member member : team.getMembers()) {
                    System.out.println("member = " + member);
                }
            };*/

            /* Member member1 = new Member();
            member1.setUsername("관리자1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            em.persist(member2);

            em.flush();
            em.clear();

            String query = "SELECT function('group_concat', m.username) From Member m";

            List<String> resultList = em.createQuery(query, String.class).getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }


            // 페치조인 - 기본 ( N+1 문제 해결 ) 예제 2
            /* for (int i = 0; i < 100; i++) { // 100명의 회원정보를 삽입
                Team team = new Team();
                team.setName("팀" + i);
                em.persist(team);

                Member member = new Member();
                member.setUsername("회원" + i);
                member.setTeam(team);
                em.persist(member);
            }

            em.flush();
            em.clear();

            String query = "select t from Team t join fetch t.members"; // join fetch 를 써줌으로써 N+1 문제 해결

            List<Team> resultList = em.createQuery(query, Team.class).getResultList();

            for (Team team : resultList) {
                System.out.println("team = " + team.getName() + ", ");
                for (Member member : team.getMembers()) {
                    // 페치 조인으로 팀과 회원을 함께 조회해서 지연 로딩 발생 안함
                    System.out.println("member = " + member.getUsername());
                }
            }*/

            // 페치조인 - 기본 ( N+1 문제 해결 ) 예제 1
            /* for (int i = 0; i < 100; i++) { // 100명의 회원정보를 삽입
                Team team = new Team();
                team.setName("팀" + i);
                em.persist(team);

                Member member = new Member();
                member.setUsername("회원" + i);
                member.setTeam(team);
                em.persist(member);
            }

            em.flush();
            em.clear();

            String query = "select m from Member m join fetch m.team"; // join fetch 를 써줌으로써 N+1 문제 해결

            List<Member> resultList = em.createQuery(query, Member.class).getResultList(); // Member를 Select 했을때 이미 Team은 프록시 객체가 아닌 찐 객체로 들어가졌다.

            for (Member member : resultList) {
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
            }*/

            /* Team team = new Team();
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

            String query = "SELECT m.id From Team t join t.members m"; // 실무에서 묵시적 조인은 치명적이다. 명시적 조인 ( 직접 조인 해주는것 ) 을 습관화 하자

            List<Long> resultList = em.createQuery(query, Long.class).getResultList();

            System.out.println("resultList = " + resultList);*/

            /* Member member1 = new Member();
            member1.setUsername("관리자1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            em.persist(member2);

            em.flush();
            em.clear();

            String query = "SELECT function('group_concat', m.username) From Member m";

            List<String> resultList = em.createQuery(query, String.class).getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            } */

            /* Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자");
            member.setAge(12);
            member.setTeam(team);
            member.setMemberType(MemberType.ADMIN);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select nullif(m.username, '관리자2') from Member m";
            List<String> resultList = em.createQuery(query, String.class).getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }*/

            /* Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            member.setTeam(team);
            member.setMemberType(MemberType.ADMIN);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m.username,'HELLO',TRUE,m.memberType from Member m where m.memberType = :userType"; // on 절을 이용하여 조건을 걸어줌
            List<Object[]> resultList = em.createQuery(query)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : resultList) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
                System.out.println("objects[2] = " + objects[2]);
                System.out.println("objects[3] = " + objects[3]);
            }*/

            /*Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m from Member m left join m.team t on t.name = 'teamA'"; // on 절을 이용하여 조건을 걸어줌
            List<Member> resultList = em.createQuery(query, Member.class)
                    .getResultList();

            System.out.println("resultList.size() = " + resultList.size());*/

            /* for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("resultList.size() = " + resultList.size());
            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }*/

            /* Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();// 엔티티 프로젝션에서 나온 결과든은 영속성 컨텍스트가 관리를 해준다.

            MemberDTO memberDTO = resultList.get(0);

            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());*/


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
