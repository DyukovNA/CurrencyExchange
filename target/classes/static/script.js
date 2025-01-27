document.addEventListener('DOMContentLoaded', () => {
    const amountInput = document.getElementById('amount');
    const fromCurrencySelect = document.getElementById('from-currency');
    const toCurrencySelect = document.getElementById('to-currency');
    const convertButton = document.getElementById('convert-button');
    const resultDiv = document.getElementById('result');

    convertButton.addEventListener('click', async () => {
        const amount = parseFloat(amountInput.value);
        const fromCurrency = fromCurrencySelect.value;
        const toCurrency = toCurrencySelect.value;

        if (isNaN(amount)) {
            resultDiv.textContent = "Введите корректную сумму.";
            return;
        }

        try {
            const fetchRate = async (currency) => {
                console.log(`Запрашиваю курс для ${currency}`);
                const response = await fetch(`http://localhost:8082/api/getData?code=${currency}`);
                console.log(`Ответ для ${currency}:`, response);
                if (!response.ok) {
                   const errorText = await response.text();
                    throw new Error(`Ошибка загрузки курса для ${currency}: ${response.status} - ${errorText}`);
                }
                 const data = await response.json();
                console.log(`Данные для ${currency}:`, data);
                return data;
            }

            if (fromCurrency == toCurrency) {
                resultDiv.textContent = `${amount} ${fromCurrency} = ${amount} ${toCurrency}`;
            } else if (toCurrency == "RUB") {
                const fromRateData = await fetchRate(fromCurrency);
                const fromRate = fromRateData.value;

                if (typeof fromRate !== 'number') {
                  throw new Error(`Некорректный формат данных от API: fromRate=${fromRate}`);
                }

                resultDiv.textContent = `${amount} ${fromCurrency} = ${fromRate * amount} ${toCurrency}`;
            } else if (fromCurrency == "RUB") {
               const toRateData = await fetchRate(toCurrency);
               const toRate = toRateData.value;
               const fromRate = 1.0;

               if (typeof toRate !== 'number') {
                 throw new Error(`Некорректный формат данных от API: toRate=${toRate}`);
               }

               const convertedAmount = amount * (fromRate / toRate);
               resultDiv.textContent = `${amount} ${fromCurrency} = ${convertedAmount.toFixed(2)} ${toCurrency}`;
            } else {
                const fromRateData = await fetchRate(fromCurrency);
                const toRateData = await fetchRate(toCurrency);

                const fromRate = fromRateData.value;
                const toRate = toRateData.value;

                if (typeof fromRate !== 'number' || typeof toRate !== 'number') {
                  throw new Error(`Некорректный формат данных от API: fromRate=${fromRate}, toRate=${toRate}`);
                }
                const convertedAmount = amount * (fromRate / toRate);
                resultDiv.textContent = `${amount} ${fromCurrency} = ${convertedAmount.toFixed(2)} ${toCurrency}`;
            }

        } catch (error) {
            console.error("Ошибка при конвертации:", error);
            resultDiv.textContent = "Ошибка конвертации. Попробуйте позже.";
        }
    });
});