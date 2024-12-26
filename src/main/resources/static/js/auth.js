// Form gönderiminde hata varsa göster
document.addEventListener('DOMContentLoaded', function() {
    const errorMessage = document.querySelector('.error-message');
    if (errorMessage) {
        errorMessage.style.display = 'block';
        setTimeout(() => {
            errorMessage.style.opacity = '0';
            setTimeout(() => {
                errorMessage.style.display = 'none';
            }, 300);
        }, 3000);
    }
});

// Form validasyonu
function validateForm() {
    const password = document.getElementById('password');
    if (password && password.value.length < 6) {
        alert('Şifre en az 6 karakter olmalıdır');
        return false;
    }
    return true;
} 