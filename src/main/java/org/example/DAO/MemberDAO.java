package org.example.DAO;

import org.example.DBConnection;
import org.example.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

    public void addMember(Member member){
        try(Connection connection = DBConnection.getConnection()) {
            String query = """
            INSERT INTO members
            (name, email, phone, department)
            VALUES (?, ?, ?, ?)
            """;
        PreparedStatement st = connection.prepareStatement(query);
        st.setString(1,member.getName());
        st.setString(3,member.getEmail());
        st.setString(2, member.getPhone());
        st.setString(4, member.getDepartment());
        int rows = st.executeUpdate();
        if(rows>0){
            System.out.println("Member Created");
        }else{
            System.out.println("Member not created");
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
  }

  public void viewAllMembers(){
      String query = "SELECT * FROM members";
      try(Connection connection = DBConnection.getConnection();
            PreparedStatement st = connection.prepareStatement(query);
          ResultSet set = st.executeQuery();)
      {
            while (set.next()){
                System.out.println(set.getString("name"));
                System.out.println(set.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
  }

  public Member searchMemberById(Long id){
        try(Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM members WHERE member_id = ?";
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1,id);
            try(ResultSet set = st.executeQuery()) {
                if(set.next()){
                    return Member
                            .builder()
                            .memberId(set.getLong("member_id"))
                            .name(set.getString("name"))
                            .email(set.getString("email"))
                            .phone(set.getString("phone"))
                            .department(set.getString("department"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
  }

  public void updateMember(Member member){
      String query = """
            UPDATE members
            SET name = ?, email = ?, phone = ?,  department = ?
            WHERE member_id = ?
            """;
      try(Connection connection = DBConnection.getConnection()) {
          PreparedStatement st = connection.prepareStatement(query);
          st.setString(1, member.getName());
          st.setString(2,member.getEmail());
          st.setString(3, member.getPhone());
          st.setString(4, member.getDepartment());
          st.setLong(5,member.getMemberId());
          int rows  = st.executeUpdate();
          if(rows>0){
              System.out.println("Member updated successfully");
          }else{
              System.out.println("Member not found");
          }
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
  }

  public void deleteMember(Long id){
        try(Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM members WHERE member_id=?";
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1,id);
            int rows = st.executeUpdate();
            if(rows>0){
                System.out.println("Member deleted Successfully");
            }else{
                System.out.println("Member not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
  }
}