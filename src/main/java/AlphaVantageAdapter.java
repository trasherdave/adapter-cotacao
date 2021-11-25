
import io.github.mainstringargs.alphavantagescraper.AlphaVantageConnector;
import io.github.mainstringargs.alphavantagescraper.StockQuotes;
import io.github.mainstringargs.alphavantagescraper.output.AlphaVantageException;
import io.github.mainstringargs.alphavantagescraper.output.quote.StockQuotesResponse;
import io.github.mainstringargs.alphavantagescraper.output.quote.data.StockQuote;
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
 * <a href="https://www.alphavantage.co">AlphaVantage</a>
 * por meio da biblioteca
 * <a href="https://github.com/mainstringargs/alpha-vantage-scraper">AlphaVantage
 * Scrapper</a>.
 *
 * @param codigoEmpresa
 */
public class AlphaVantageAdapter implements AdapterServicoCotacao {

    @Override
    public double cotacao(String codigoEmpresa) {
        double preco = 0.0;

        System.out.printf("Cotação da Empresa %s obtida pelo serviço Alpha Vantage: http://www.alphavantage.co%n", codigoEmpresa);

        /*
        Verifica se existe uma variável de ambiente para a chave da API do serviço Alpha Vantage.
        Você pode se cadastrar e obter uma chave em http://www.alphavantage.co
         */
        final String s = System.getenv("ALPHAVANTAGE_APIKEY");
        final String apiKey = s == null ? "50M3AP1K3Y" : s;
        final int timeout = 3000;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);

        //Permite obter a cotação (quotes) de ações (stocks).
        StockQuotes stockQuotes = new StockQuotes(apiConnector);

        try {
            StockQuotesResponse response = stockQuotes.quote(codigoEmpresa);
            StockQuote stock = response.getStockQuote();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            System.out.printf("Data: %s Preço: %s%n", formatter.format(stock.getLatestTradingDay()), stock.getPrice());

            preco = stock.getPrice();

        } catch (AlphaVantageException e) {
            System.out.println("Erro ao solicitar cotação da empresa " + codigoEmpresa + ": " + e.getMessage());
        }
        System.out.println("---------------------------------------------------------------------");

        return preco;
    }

}
