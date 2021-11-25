
import io.github.mainstringargs.yahooFinance.YahooFinanceData;
import io.github.mainstringargs.yahooFinance.YahooFinanceModules;
import io.github.mainstringargs.yahooFinance.YahooFinanceRequest;
import io.github.mainstringargs.yahooFinance.YahooFinanceUrlBuilder;
import io.github.mainstringargs.yahooFinance.domain.FinancialData;

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
 * <a href="https://finance.yahoo.com">Yahoo Finance</a>
 * por meio da biblioteca
 * <a href="https://github.com/mainstringargs/yahoo-finance-scraper">Yahoo
 * Finance Scrapper</a>.
 *
 * @param codigoEmpresa
 * @see
 * <a href="http://meumobi.github.io/stocks%20apis/2016/03/13/get-realtime-stock-quotes-yahoo-finance-api.html">Get
 * realtime stock quotes yahoo finance API</a>
 */
public class YahooAdapter implements AdapterServicoCotacao {

    @Override
    public double cotacao(String codigoEmpresa) {
        double preco = 0.0;

        System.out.printf("Cotação da Empresa %s obtida pelo serviço Yahoo Finance: https://finance.yahoo.com%n", codigoEmpresa);
        YahooFinanceUrlBuilder builder
                = new YahooFinanceUrlBuilder().modules(YahooFinanceModules.values()).symbol(codigoEmpresa);

        YahooFinanceRequest request = new YahooFinanceRequest();
        YahooFinanceData financeData = request.getFinanceData(request.invoke(builder));

        FinancialData financials = financeData.getFinancialData();
        if (financials != null) {
            System.out.printf("Preço: %s %s%n", financials.getFinancialCurrency(), financials.getCurrentPrice().getRaw());

            preco = financials.getCurrentPrice().getRaw().doubleValue();

        }

        System.out.println(builder.getURL());
        System.out.println("https://query1.finance.yahoo.com/v8/finance/chart/" + codigoEmpresa + "?period1=1546311600&period2=1556593200&interval=1d&includePrePost=False");
        System.out.println("---------------------------------------------------------------------");

        return preco;
    }

}
