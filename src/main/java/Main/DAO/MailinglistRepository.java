package Main.DAO;

import Main.DAO.Interfaces.ImailRepository;
import Main.Model.Mailinglist;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MailinglistRepository implements ImailRepository {

    Connection connection = SingletonConnection.getConnexion();

    @Override
    public void addEmail(String email) {

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO mailinglist(Email) " +
                    "VALUES (?)") ;
            ps.setString(1,email);

            ps.executeUpdate();

            System.out.println("mail ajouté");

        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,"Erreur de connection","Erreur", JOptionPane.ERROR_MESSAGE);

        }

    }

    @Override
    public List<Mailinglist> getAllMails() {
        List<Mailinglist> maillist = new ArrayList<>();
        Connection connection = SingletonConnection.getConnexion();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM mailinglist");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Mailinglist mailinglist = new Mailinglist(rs.getString("Email"));

                maillist.add(mailinglist);
                System.out.println(maillist);
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null,"Erreur de connection","Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return maillist;
    }

    @Override
    public int GetTotalSubs() {
        int nbsubs =0 ;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT count(*) as total from mailinglist ");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nbsubs=rs.getInt("total") ;
            }
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null,"Erreur de connection","Erreur", JOptionPane.ERROR_MESSAGE);
        }

        return nbsubs;    }
}
