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

            Member member = em.find(Member.class, 150L);
            member.setName("AAAAAA");

            em.clear();
            Member member1 = em.find(Member.class, 150L);

            System.out.println("==========================");

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
