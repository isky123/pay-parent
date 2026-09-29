document.addEventListener('DOMContentLoaded', () => {
  const submitBtn = document.getElementById('submitBtn');
  const resultBox = document.getElementById('resultBox');

  submitBtn.addEventListener('click', async () => {
    const payload = {
      merchantOrderNo: document.getElementById('orderNo').value,
      amount: Number(document.getElementById('amount').value),
      currency: 'CNY',
      subject: document.getElementById('subject').value,
      channel: document.getElementById('channel').value,
      clientIp: '127.0.0.1',
      returnUrl: 'https://example.com/return',
      notifyUrl: 'https://example.com/notify'
    };

    resultBox.textContent = '正在提交...';
    try {
      const response = await fetch('/api/v1/payments', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });
      const text = await response.text();
      resultBox.textContent = text;
    } catch (error) {
      resultBox.textContent = '请求失败：' + error.message;
    }
  });
});
