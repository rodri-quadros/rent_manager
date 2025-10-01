package com.user.gestaoaluguel.dao;

import com.user.gestaoaluguel.config.DataBase;
import com.user.gestaoaluguel.config.Morador;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalTime;
import java.time.LocalDateTIme;


public class MoradorDao {
    public list<Morador> activeToList(){
        String sql = "SELECT * FROM morador WHERE active = 1 ORDER BY name";
        List <Morador> list = new ArrayList<>();
        try (Connection c = DataBase.getConnection())
            PreparedStatement ps = c.prepareStatement(sql))
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map (rs);
            }
        } catch (SQLException e) {
            throw new RunTimeException("Erro ao buscar morador ", e)
    }
    return list;
}

    public Morador searchForId(int id) {
        String sql = "¨SELECT * FROM morador WHERE id = ?";
        try (Connection c = DataBase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
                return null;
            }
        } catch (SQLException e) {
            throw new RunTimeException("Erro ao buscar morador ", e);
        }
    }

    public int insert (Morador m) {
        String sql = """
                    INSERT INTO morador
                      (name, document, house_number, phone_number, rent_value, pay_staus, pay_date, active)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection c = DataBase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getName());
            ps.setString(2, m.getDocument());
            ps.setString(3, m.getHouseNumber());
            ps.setString(4, m.getPhoneNumber());
            ps.setBigDecimal(5, m.getRentValue() == null ? BigDecimal.ZERO : m.getRentValue());
            ps.setString(6, toDateStr(m.getDueDate()));
            ps.setInt(7, m.PayStatus() ? 1 : 0);
            ps.setString(8, toDateTimeStr(m.getPayDate()));
            ps.setInt(9, m.isActive() ? 1 : 0);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir morador", e);
        }
    }
    public void update (Morador m) {
        String sql = """
                UPDATE morador SET
                  name=?, document=?, house_number=?, phoneNumber=?,
                  rent_value=?, pay_status=?, pay_date=?, active=?
                WHERE id=?
            """;
        try (Connection c = DataBase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getName());
            ps.setString(2, m.getDocument());
            ps.setString(3, m.getHouseNumber());
            ps.setString(4, m.getPhoneNumber());
            ps.setBigDecimal(5, m.getRentValue() == null ? BigDecimal.ZERO : m.getRentValue());
            ps.setString(6, toDateStr(m.getDueDate()));
            ps.setInt(7, m.isPago() ? 1 : 0);
            ps.setString(8, toDateTimeStr(m.getPayDate()));
            ps.setInt(9, m.isActive() ? 1 : 0);
            ps.setInt(10, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar morador", e);
        }
    }
    public void removePhysical (int id) {
        String sql = "DELETE FROM morador WHERE id=?";
        try (Connection c = DataBase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover morador", e);
        }
    }
    public void inactive(int id) {
        String sql = "UPDATE morador SET active=0 WHERE id=?";
        try (Connection c = DataBase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inativar morador", e);
        }
    }
    public void payCheck (int id, boolean payStatus, LocalDateTime when) {
        String sql = "UPDATE morador SET pay_status=?, pay_date=? WHERE id=?";
        try (Connection c = DataBase.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pago ? 1 : 0);
            ps.setString(2, pago ? toDateTimeStr(when) : null);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao marcar pago", e);
        }
    }
    private Morador map(ResultSet rs) throws SQLException {
        Morador m = new Morador();
        m.setId(rs.getInt("id"));
        m.setName(rs.getString("name"));
        m.setDocument(rs.getString("document"));
        m.setHouseNumber(rs.getString("house_number"));
        m.setPhoneNumber(rs.getString("phone_number"));
        m.setRentValue(rs.getBigDecimal("rent_value"));
        m.setDueDate(fromDateStr(rs.getString("due_date")));
        m.setPayStatus(rs.getInt("pay_status") == 1);
        m.setPayDate(fromDateTimeStr(rs.getString("pay_date")));
        m.setActive(rs.getInt("active") == 1);
        return m;
    }
    private static String toDateStr(LocalDate d) {
        return d == null ? null : d.toString();
    }
    private static LocalDate fromDateStr(String s) {
        return (s == null || s.isBlank()) ? null : LocalDate.parse(s);
    }
    private static String toDateTimeStr(LocalDateTime dt) {
        return dt == null ? null : dt.toString().substring(0,19);
    }
    private static LocalDateTime fromDateTimeStr(String s) {
        return (s == null || s.isBlank()) ? null : LocalDateTime.parse(s);
    }
 }