package com.example.demo.config.batch.reader;

import com.example.demo.models.entity.OutstandingEntity;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CobroAutomaticoSemanalReader extends JdbcCursorItemReader<OutstandingEntity> implements ItemReader<OutstandingEntity> {


    public CobroAutomaticoSemanalReader(@Autowired DataSource primaryDataSource) {
        setDataSource(primaryDataSource);
        setSql("SELECT * FROM outstanding where tipo_cobros like 'AUTOMATICO' AND cantidad_restante != 0 AND periodicidad like 'SEMANAL'");
        setFetchSize(100);
        setRowMapper(new CobroAutomaticoSemanalReader.OutstandingRowMapper());
    }

    public class OutstandingRowMapper implements RowMapper<OutstandingEntity> {
        @Override
        public OutstandingEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

            OutstandingEntity outstanding  = new OutstandingEntity();
            outstanding.setIdOutstanding(rs.getInt("id_outstanding"));
            outstanding.setCantidadRestante(rs.getDouble("cantidad_restante"));
            outstanding.setFechaEfectiva(rs.getDate("fecha_efectiva"));
            outstanding.setFechaCreacion(rs.getDate("fecha_creacion"));
            outstanding.setFechaFinalizacion(rs.getDate("fecha_finalizacion"));
            outstanding.setPagoPrincipal(rs.getDouble("pago_principal"));
            outstanding.setPagoIntereses(rs.getDouble("pago_intereses"));
            outstanding.setTipoInteres(rs.getInt("tipo_interes"));
            outstanding.setTipoCobros(rs.getString("tipo_cobros"));
            outstanding.setPeriodicidad(rs.getString("periodicidad"));
            outstanding.setCantidadCobroPeriodico(rs.getDouble("cantidad_cobro_periodico"));
            outstanding.setFacility(rs.getInt("facility"));

            return outstanding;
        }
    }

}