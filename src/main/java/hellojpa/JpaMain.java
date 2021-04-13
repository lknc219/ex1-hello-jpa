package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        //웹서버가 실행될때 한 번 생성해서 전체 애플리케이션에서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //요청사항을 수행할 때 마다 한 번 쓰고 버려야함. 쓰레드마다 공유 X
        //모든 데이터 변경은 트랜잭션 안에서 수행해야함.
        EntityManager em = emf.createEntityManager();//디비커넥션 하나 받았다고 생각하면됨

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            //Member findMember = em.find(Member.class, 1L);
            //findMember.setName("HelloJPA");
            //commit 직전 변경사항이 있으면 update쿼리를 날려서 update 함.

            //비영속

            //영속: 영속성 컨텍스트에 포함되어 관리 되는 상태

            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("Member1");
            //연관관계의 주인에 값을 세팅해주는 부분 Member 와 Team 객체의 연관관계의 주인은 Member.team 이다
            //setTeam -> changeTeam 으로 바꿔주면서 team을 셋팅할때 team.getMembers().add(this) 를 해줌으로
            //양방향으로 셋팅을 하는 메서드로 만들어준다 (연관관계 편의 메서드라고 한다.)
            member.changeTeam(team);
            team.addMember(member);
            em.persist(member);

            //연관관계의 주인은 아니지만 둘다 셋팅해주는게 좋다.
            //DB에서 읽지않고 영속성 컨텍스트의 순수 객체를 읽을때 못 읽어오는 경우가 있기 떄문
            //team.getMembers().add(member);

//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();

            System.out.println("===========================");
            for (Member m : members) {
                System.out.println("m.get = " + m.getUsername());
            }
            System.out.println("===========================");
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
