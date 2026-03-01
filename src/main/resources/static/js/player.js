let currentAudio = null;
let currentSongId = null;

function buttonPlaySong(songId, songTitle, songArtist, audioUrl) {
	// Если нажали на ту же песню, что уже играет
    if (currentAudio && currentSongId === songId && !currentAudio.paused) {
        return; // ничего не делаем
    }
	
    // Останавливаем текущее воспроизведение, если есть
    if (currentAudio) {
        currentAudio.pause();
    }
    
    // Создаём новый аудио элемент
    currentAudio = new Audio(audioUrl);
    currentAudio.play();
    currentSongId = songId;
    
    // Обновляем информацию в плеере
    document.getElementById('player-song-title').textContent = songTitle;
    document.getElementById('player-song-artist').textContent = songArtist;
    currentAudio.addEventListener('loadedmetadata', function() {
        document.getElementById('player-duration').textContent = formatTime(currentAudio.duration);
    });
	
    // Обновляем прогресс бар
    currentAudio.addEventListener('timeupdate', updateProgress);

	// Меняем кнопку на паузу
	document.getElementById('player-btn-play-pause').textContent = '⏸';
    
    // Когда песня заканчивается
    currentAudio.addEventListener('ended', function() {
        document.getElementById('player-btn-play-pause').textContent = '▶';
        document.getElementById('progress-time').textContent = '0:00';
        document.getElementById('progress-fill').style.width = '0%';
    });
}

function updateProgress() {
    if (!currentAudio) return;
    
    const progressPercent = (currentAudio.currentTime / currentAudio.duration) * 100;
    
    document.getElementById('progress-time').textContent = formatTime(currentAudio.currentTime);
    document.getElementById('progress-fill').style.width = progressPercent + '%';
}

function formatTime(seconds) {
    if (isNaN(seconds)) return '0:00';
    const mins = Math.floor(seconds / 60);
    const secs = Math.floor(seconds % 60);
    return mins + ':' + (secs < 10 ? '0' : '') + secs;
}

function buttonPausePlay() {
    if (!currentAudio) return;
    
    if (currentAudio.paused) {
        currentAudio.play();
        document.getElementById('player-btn-play-pause').textContent = '⏸';
    } else {
        currentAudio.pause();
        document.getElementById('player-btn-play-pause').textContent = '▶';
    }
}

// Функция для перемотки (потом реализуем)
function buttonSeek(event) {
    // TODO: реализовать в следующем пункте
}