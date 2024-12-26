function toggleFabMenu() {
    var fabMenu = document.getElementById('fabMenu');
    if (fabMenu.style.display === 'none' || fabMenu.style.display === '') {
        fabMenu.style.display = 'block';
    } else {
        fabMenu.style.display = 'none';
    }
}

function showUploadFileModal() {
    var uploadFileModal = document.getElementById('uploadFileModal');
    uploadFileModal.style.display = 'flex';
}

function uploadFile() {
    var fileInput = document.getElementById('fileInput');
    var file = fileInput.files[0];
    var userId = document.body.getAttribute('data-user-id');
    if (file) {
        var formData = new FormData();
        formData.append('file', file);
        formData.append('userId', userId);

        fetch('/api/files/upload', {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.ok) {
                loadItems();
                document.getElementById('uploadFileModal').style.display = 'none';
            } else {
                alert('Dosya yüklenemedi');
            }
        }).catch(error => {
            console.error('Dosya yükleme hatası:', error);
            alert('Dosya yüklenemedi');
        });
    }
}

function deleteFile(fileId) {
    fetch('/api/files/delete/' + fileId, {
        method: 'DELETE'
    }).then(response => {
        if (response.ok) {
            loadItems();
        } else {
            alert('Dosya silinemedi');
        }
    });
}

function downloadFile(fileId) {
    fetch('/api/files/download/' + fileId)
        .then(response => response.blob())
        .then(blob => {
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement('a');
            a.href = url;
            a.download = 'file.zip';
            document.body.appendChild(a);
            a.click();
            a.remove();
        })
        .catch(() => alert('Dosya indirilemedi'));
}

let currentFileId = null;

function showShareFileModal(fileId) {
    currentFileId = fileId;
    document.getElementById('shareFileModal').style.display = 'flex';
    document.getElementById('recipientEmail').value = '';
}

function shareFile() {
    const recipientEmail = document.getElementById('recipientEmail').value;
    if (!recipientEmail) {
        alert('Lütfen bir e-posta adresi girin');
        return;
    }

    const formData = new URLSearchParams();
    formData.append('recipientEmail', recipientEmail);

    fetch('/api/files/share/' + currentFileId, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text) });
        }
        return response.json();
    })
    .then(() => {
        alert('Dosya başarıyla paylaşıldı');
        document.getElementById('shareFileModal').style.display = 'none';
    })
    .catch(error => {
        alert('Hata: ' + error.message);
    });
}

function toggleDropdown(event, fileId) {
    event.stopPropagation();
    var dropdowns = document.querySelectorAll('.dropdown-content');
    dropdowns.forEach(function(dropdown) {
        if (dropdown.getAttribute('data-file-id') !== fileId.toString()) {
            dropdown.classList.remove('show');
        }
    });
    
    var dropdownContent = event.currentTarget.nextElementSibling;
    dropdownContent.classList.toggle('show');
}

function loadItems() {
    var userId = document.body.getAttribute('data-user-id');
    fetch('/api/files/listFiles/' + userId)
        .then(response => response.json())
        .then(files => {
            var itemsContainer = document.getElementById('items-container');
            itemsContainer.innerHTML = '';

            if (Array.isArray(files)) {
                files.forEach(file => {
                    let fileName = file.fileName;
                    let lastDotIndex = fileName.lastIndexOf('.');
                    if (lastDotIndex !== -1) {
                        fileName = fileName.substring(0, lastDotIndex);
                    }

                    let displayName = fileName;
                    if (displayName.length > 40) {
                        displayName = displayName.substring(0, 40) + '...';
                    }

                    var fileElement = document.createElement('div');
                    fileElement.className = 'file';
                    fileElement.innerHTML = `
                        <div>
                            <p style="word-break: break-word; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden;">${displayName}</p>
                            ${file.user ? `<small class="file-owner">Sahibi: ${file.user.name}</small>` : ''}
                        </div>
                        <div class="dropdown">
                            <button class="dropdown-toggle" onclick="toggleDropdown(event, ${file.fileId})">⋮</button>
                            <div class="dropdown-content" data-file-id="${file.fileId}">
                                <a href="#" onclick="deleteFile(${file.fileId}); return false;">
                                    <i class="fas fa-trash-alt icon-delete"></i> Sil
                                </a>
                                <a href="#" onclick="downloadFile(${file.fileId}); return false;">
                                    <i class="fas fa-download icon-download"></i> İndir
                                </a>
                                <a href="#" onclick="showShareFileModal(${file.fileId}); return false;">
                                    <i class="fas fa-share-alt icon-share"></i> Paylaş
                                </a>
                            </div>
                        </div>
                    `;
                    itemsContainer.appendChild(fileElement);
                });
            }
        });
}

// Sayfa dışı tıklamaları dinle
window.onclick = function(event) {
    if (!event.target.matches('.dropdown, .dropdown *')) {
        var dropdowns = document.querySelectorAll('.dropdown-content');
        dropdowns.forEach(function(dropdown) {
            dropdown.classList.remove('show');
        });
    }

    var uploadFileModal = document.getElementById('uploadFileModal');
    var shareFileModal = document.getElementById('shareFileModal');
    if (event.target == uploadFileModal) {
        uploadFileModal.style.display = 'none';
    }
    if (event.target == shareFileModal) {
        shareFileModal.style.display = 'none';
    }
}

// Sayfa yüklendiğinde dosyaları yükle
document.addEventListener('DOMContentLoaded', loadItems); 