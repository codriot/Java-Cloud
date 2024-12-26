function showUserForm() {
    document.getElementById('usersList').style.display = 'none';
    document.getElementById('userForm').style.display = 'block';
    document.getElementById('dashboardContent').style.display = 'none';
    document.querySelectorAll('.nav-card').forEach(card => card.classList.remove('active'));
    document.querySelectorAll('.nav-card')[2].classList.add('active');
}

function showUsersList() {
    document.getElementById('usersList').style.display = 'block';
    document.getElementById('userForm').style.display = 'none';
    document.getElementById('dashboardContent').style.display = 'none';
    document.querySelectorAll('.nav-card').forEach(card => card.classList.remove('active'));
    document.querySelectorAll('.nav-card')[1].classList.add('active');
}

function showDashboard() {
    document.getElementById('usersList').style.display = 'none';
    document.getElementById('userForm').style.display = 'none';
    document.getElementById('dashboardContent').style.display = 'block';
    document.querySelectorAll('.nav-card').forEach(card => card.classList.remove('active'));
    document.querySelectorAll('.nav-card')[0].classList.add('active');
    loadItems(); // Dosyaları yükle
}

// Sayfa yüklendiğinde dashboard'ı göster
document.addEventListener('DOMContentLoaded', function() {
    showDashboard();
});

// Kullanıcılar kartına tıklandığında
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.nav-card')[1].addEventListener('click', showUsersList);
}); 