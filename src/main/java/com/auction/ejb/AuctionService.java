package com.auction.ejb;

import com.auction.model.Actions;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Stateless
public class AuctionService {

    @Resource(lookup = "jdbc/auctionDS") // meka oyage payara datasource name ekata match wenna one
    private DataSource dataSource;

    public Actions getAuctionById(int id) {
        Actions auction = null;

        String sql = "SELECT * FROM actions WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                auction = new Actions();
                auction.setId(rs.getInt("id"));
                auction.setTitle(rs.getString("title"));
                auction.setDescription(rs.getString("description"));
                auction.setStartPrice(rs.getDouble("start_price"));
                auction.setEndDate(rs.getDate("end_date"));
                auction.setImgUrl(rs.getString("image_url"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return auction;
    }
}
