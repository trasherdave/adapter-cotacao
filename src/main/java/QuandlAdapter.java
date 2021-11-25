
import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.Row;
import com.jimmoores.quandl.TabularResult;
import com.jimmoores.quandl.classic.ClassicQuandlSession;
import java.time.format.DateTimeFormatter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author David
 */
/**
 * Acessa a cotação de uma determinada empresa utilizando o serviço do
 * <a href="https://www.quandl.com">Quandl</a>
 * por meio da biblioteca <a href="http://quandl4j.org">quandl4j</a>.
 *
 * @param codigoEmpresa
 */
public class QuandlAdapter implements AdapterServicoCotacao {

    @Override
    public double cotacao(String codigoEmpresa) {
        double preco = 0.0;

        System.out.printf("Cotação da Empresa %s obtida pelo serviço Quandl: http://quandl.com/%n", codigoEmpresa);
        ClassicQuandlSession session = ClassicQuandlSession.create();
        DataSetRequest request = DataSetRequest.Builder
                .of(codigoEmpresa)
                .withMaxRows(1)
                .build();
        TabularResult result = session.getDataSet(request);
        if (result.size() > 0) {
            Row row = result.get(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date = formatter.format(row.getLocalDate("Date"));
            System.out.printf("Data: %s Preço: %s%n", date, row.getDouble("Close"));
            //System.out.println(result.toPrettyPrintedString());

            preco = row.getDouble("Close");
        }
        System.out.println("---------------------------------------------------------------------");

        return preco;
    }

}
