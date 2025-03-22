package com.example.unscramble.data


const val MAX_NO_OF_WORDS = 10
const val SCORE_INCREASE = 20

// Set with all the words for the Game
data class Word(
    private val _word: String,
    private val _meaning: String,
//    private val _partOfSpeech: String,
//    private val _difficulty: WordDifficulty
){
    val word: String
        get() = _word

    val meaning: String
        get() = _meaning
}

val allWords: List<Word> = listOf(
    Word("animal", "Động vật"),
    Word("auto", "Ô tô, tự động"),
    Word("anecdote", "Giai thoại, chuyện vặt"),
    Word("alphabet", "Bảng chữ cái"),
    Word("all", "Tất cả, toàn bộ"),
    Word("awesome", "Tuyệt vời, đáng kinh ngạc"),
    Word("arise", "Nảy sinh, xuất hiện"),
    Word("balloon", "Bóng bay"),
    Word("basket", "Giỏ, rổ"),
    Word("bench", "Băng ghế, ghế dài"),
    Word("best", "Tốt nhất"),
    Word("birthday", "Sinh nhật"),
    Word("book", "Sách, đặt chỗ"),
    Word("briefcase", "Cặp tài liệu"),
    Word("camera", "Máy ảnh, máy quay"),
    Word("camping", "Cắm trại"),
    Word("candle", "Nến"),
    Word("cat", "Mèo"),
    Word("cauliflower", "Súp lơ trắng"),
    Word("chat", "Trò chuyện, tán gẫu"),
    Word("children", "Trẻ em"),
    Word("class", "Lớp học, hạng"),
    Word("classic", "Kinh điển, cổ điển"),
    Word("classroom", "Phòng học"),
    Word("coffee", "Cà phê"),
    Word("colorful", "Sặc sỡ, đầy màu sắc"),
    Word("cookie", "Bánh quy"),
    Word("creative", "Sáng tạo"),
    Word("cruise", "Du thuyền, hành trình trên biển"),
    Word("dance", "Nhảy múa, khiêu vũ"),
    Word("daytime", "Ban ngày"),
    Word("dinosaur", "Khủng long"),
    Word("doorknob", "Tay nắm cửa"),
    Word("dine", "Ăn tối"),
    Word("dream", "Giấc mơ, mơ ước"),
    Word("dusk", "Hoàng hôn, chạng vạng"),
    Word("eating", "Ăn uống"),
    Word("elephant", "Voi"),
    Word("emerald", "Ngọc lục bảo, màu xanh lục"),
    Word("eerie", "Kỳ lạ, rùng rợn"),
    Word("electric", "Điện, chạy bằng điện"),
    Word("finish", "Hoàn thành, kết thúc"),
    Word("flowers", "Hoa"),
    Word("follow", "Theo dõi, đi theo"),
    Word("fox", "Cáo"),
    Word("frame", "Khung, cấu trúc"),
    Word("free", "Miễn phí, tự do"),
    Word("frequent", "Thường xuyên"),
    Word("funnel", "Cái phễu, hình nón"),
    Word("green", "Màu xanh lá cây, thân thiện môi trường"),
    Word("guitar", "Đàn guitar"),
    Word("grocery", "Cửa hàng tạp hóa, thực phẩm"),
    Word("glass", "Thủy tinh, kính"),
    Word("great", "Tuyệt vời, to lớn"),
    Word("giggle", "Cười khúc khích"),
    Word("haircut", "Cắt tóc"),
    Word("half", "Một nửa"),
    Word("homemade", "Tự làm tại nhà"),
    Word("happen", "Xảy ra"),
    Word("honey", "Mật ong, người yêu"),
    Word("hurry", "Vội vàng, gấp gáp"),
    Word("hundred", "Một trăm"),
    Word("ice", "Băng, đá lạnh"),
    Word("igloo", "Lều tuyết"),
    Word("invest", "Đầu tư"),
    Word("invite", "Mời"),
    Word("icon", "Biểu tượng, thần tượng"),
    Word("introduce", "Giới thiệu"),
    Word("joke", "Trò đùa, nói đùa"),
    Word("jovial", "Vui vẻ, hoạt bát"),
    Word("journal", "Tạp chí, nhật ký"),
    Word("jump", "Nhảy"),
    Word("join", "Tham gia, kết nối"),
    Word("kangaroo", "Chuột túi"),
    Word("keyboard", "Bàn phím, đàn organ"),
    Word("kitchen", "Nhà bếp"),
    Word("koala", "Gấu túi"),
    Word("kind", "Tử tế, loại"),
    Word("kaleidoscope", "Kính vạn hoa"),
    Word("landscape", "Phong cảnh, cảnh quan"),
    Word("late", "Muộn, trễ"),
    Word("laugh", "Cười"),
    Word("learning", "Học tập"),
    Word("lemon", "Quả chanh"),
    Word("letter", "Lá thư, chữ cái"),
    Word("lily", "Hoa ly"),
    Word("magazine", "Tạp chí"),
    Word("marine", "Biển, thuộc về biển"),
    Word("marshmallow", "Kẹo dẻo"),
    Word("maze", "Mê cung"),
    Word("meditate", "Thiền, suy ngẫm"),
    Word("melody", "Giai điệu"),
    Word("minute", "Phút, nhỏ bé"),
    Word("monument", "Đài tưởng niệm, tượng đài"),
    Word("moon", "Mặt trăng"),
    Word("motorcycle", "Xe máy"),
    Word("mountain", "Núi"),
    Word("music", "Âm nhạc"),
    Word("north", "Phía bắc"),
    Word("nose", "Mũi"),
    Word("night", "Ban đêm"),
    Word("name", "Tên, đặt tên"),
    Word("never", "Không bao giờ"),
    Word("negotiate", "Đàm phán, thương lượng"),
    Word("number", "Số, con số"),
    Word("ocean", "Đại dương"),
    Word("octopus", "Bạch tuộc"),
    Word("orange", "Quả cam, màu cam"),
    Word("owl", "Cú mèo"),
    Word("pencil", "Bút chì"),
    Word("penguin", "Chim cánh cụt"),
    Word("piano", "Đàn piano"),
    Word("planet", "Hành tinh"),
    Word("pocket", "Túi nhỏ, túi quần"),
    Word("puzzle", "Câu đố, xếp hình"),
    Word("quilt", "Chăn bông"),
    Word("rabbit", "Con thỏ"),
    Word("rainbow", "Cầu vồng"),
    Word("restaurant", "Nhà hàng"),
    Word("river", "Dòng sông"),
    Word("rocket", "Tên lửa"),
    Word("sailboat", "Thuyền buồm"),
    Word("sandwich", "Bánh sandwich"),
    Word("school", "Trường học"),
    Word("season", "Mùa"),
    Word("shadow", "Cái bóng"),
    Word("snowflake", "Bông tuyết"),
    Word("star", "Ngôi sao"),
    Word("sun", "Mặt trời"),
    Word("telescope", "Kính viễn vọng"),
    Word("thunder", "Sấm sét"),
    Word("tiger", "Hổ"),
    Word("train", "Tàu hỏa"),
    Word("umbrella", "Cái ô, dù"),
    Word("volcano", "Núi lửa"),
    Word("whale", "Cá voi"),
    Word("window", "Cửa sổ"),
    Word("zebra", "Ngựa vằn"),
    Word("zoo", "Sở thú"),
    Word("account", "Tài khoản, báo cáo, lời giải thích"),
    Word("act", "Hành động, diễn, luật"),
    Word("address", "Địa chỉ, bài phát biểu, giải quyết"),
    Word("affect", "Ảnh hưởng, tác động"),
    Word("air", "Không khí, kiểu dáng, phát sóng"),
    Word("angle", "Góc, quan điểm"),
    Word("apply", "Áp dụng, nộp đơn"),
    Word("arm", "Cánh tay, vũ khí, chi nhánh"),
    Word("balance", "Cân bằng, số dư"),
    Word("bar", "Quầy bar, thanh, rào cản"),
    Word("base", "Cơ sở, nền tảng, căn cứ quân sự"),
    Word("bat", "Gậy, con dơi"),
    Word("beach", "Bãi biển"),
    Word("board", "Bảng, tấm ván, lên tàu"),
    Word("book", "Sách, đặt chỗ"),
    Word("bottle", "Chai, lọ"),
    Word("bottom", "Đáy, phía dưới"),
    Word("box", "Hộp, đấm"),
    Word("break", "Nghỉ, đập vỡ, thay đổi"),
    Word("bridge", "Cầu, nối kết"),
    Word("broad", "Rộng, rộng rãi"),
    Word("budget", "Ngân sách, chi phí"),
    Word("bunch", "Chùm, bó"),
    Word("call", "Gọi, cuộc gọi, yêu cầu"),
    Word("capital", "Thủ đô, vốn, tài sản"),
    Word("car", "Xe ô tô, phương tiện di chuyển"),
    Word("case", "Trường hợp, vỏ, kiện án"),
    Word("catch", "Bắt, bắt kịp, sự bắt được"),
    Word("center", "Trung tâm, trung ương"),
    Word("charge", "Sạc, phí, tấn công"),
    Word("cheese", "Phô mai"),
    Word("chip", "Mảnh, khoai tây chiên, mạch điện"),
    Word("clean", "Sạch sẽ, làm sạch"),
    Word("club", "Câu lạc bộ, gậy bóng chày"),
    Word("cold", "Lạnh, cảm lạnh"),
    Word("comment", "Bình luận, ý kiến"),
    Word("company", "Công ty, bạn đồng hành"),
    Word("contact", "Liên hệ, tiếp xúc"),
    Word("cookie", "Bánh quy, mật mã"),
    Word("copy", "Bản sao, sao chép"),
    Word("corner", "Góc, chỗ rẽ"),
    Word("cost", "Chi phí, giá trị"),
    Word("course", "Khóa học, hành trình"),
    Word("credit", "Tín dụng, ghi nhận"),
    Word("cut", "Cắt, giảm thiểu"),
    Word("date", "Ngày tháng, hẹn hò"),
    Word("deal", "Thỏa thuận, giao dịch"),
    Word("define", "Định nghĩa, xác định"),
    Word("department", "Phòng ban, bộ phận"),
    Word("design", "Thiết kế, sự thiết kế"),
    Word("difference", "Sự khác biệt"),
    Word("direct", "Trực tiếp, chỉ đạo"),
    Word("drive", "Lái, sự điều khiển, nỗ lực"),
    Word("drop", "Rơi, giọt, bỏ xuống"),
    Word("dust", "Bụi, dọn dẹp"),
    Word("early", "Sớm, ban đầu"),
    Word("edge", "Lề, cạnh, viền"),
    Word("end", "Kết thúc, rìa"),
    Word("environment", "Môi trường"),
    Word("example", "Ví dụ"),
    Word("exercise", "Bài tập, tập luyện"),
    Word("face", "Mặt, đối mặt, đối diện"),
    Word("fall", "Ngã, mùa thu"),
    Word("fan", "Quạt, người hâm mộ"),
    Word("fast", "Nhanh, ăn kiêng"),
    Word("fine", "Tốt, phạt tiền"),
    Word("finish", "Hoàn thành, kết thúc"),
    Word("flower", "Hoa"),
    Word("floor", "Sàn nhà"),
    Word("fly", "Bay, con ruồi"),
    Word("focus", "Tập trung, tiêu điểm"),
    Word("force", "Lực, ép buộc"),
    Word("form", "Hình thức, mẫu đơn"),
    Word("free", "Miễn phí, tự do"),
    Word("game", "Trò chơi, ván bài"),
    Word("garden", "Vườn"),
    Word("gift", "Quà tặng, tài năng"),
    Word("glass", "Kính, thủy tinh"),
    Word("go", "Đi, hoạt động"),
    Word("good", "Tốt, tuyệt vời"),
    Word("group", "Nhóm"),
    Word("hard", "Khó, cứng"),
    Word("head", "Đầu, lãnh đạo"),
    Word("heart", "Trái tim"),
    Word("heat", "Nhiệt, làm nóng"),
    Word("heavy", "Nặng nề"),
    Word("help", "Giúp đỡ, trợ giúp"),
    Word("history", "Lịch sử"),
    Word("hold", "Cầm nắm, tổ chức"),
    Word("home", "Nhà"),
    Word("hope", "Hy vọng"),
    Word("hotel", "Khách sạn"),
    Word("hour", "Giờ, một khoảng thời gian"),
    Word("house", "Nhà, hộ gia đình"),
    Word("imagine", "Tưởng tượng"),
    Word("include", "Bao gồm"),
    Word("increase", "Tăng lên, gia tăng"),
    Word("interest", "Sở thích, lãi suất"),
    Word("join", "Tham gia, kết nối"),
    Word("joke", "Trò đùa, nói đùa"),
    Word("jump", "Nhảy"),
    Word("key", "Chìa khóa, yếu tố quan trọng"),
    Word("kind", "Tử tế, loại"),
    Word("language", "Ngôn ngữ"),
    Word("large", "Lớn"),
    Word("last", "Cuối cùng, trước đây"),
    Word("late", "Muộn"),
    Word("lead", "Dẫn, chì, chỉ huy"),
    Word("learn", "Học, tìm hiểu"),
    Word("leave", "Rời đi, bỏ lại"),
    Word("light", "Ánh sáng, nhẹ"),
    Word("like", "Thích, giống"),
    Word("line", "Dòng, đường thẳng"),
    Word("list", "Danh sách, liệt kê"),
    Word("live", "Sống, trực tiếp"),
    Word("look", "Nhìn, vẻ ngoài"),
    Word("lose", "Mất, thua"),
    Word("lunch", "Bữa trưa"),
    Word("machine", "Máy móc"),
    Word("main", "Chính, chủ yếu"),
    Word("make", "Tạo, làm"),
    Word("manage", "Quản lý, điều hành"),
    Word("man", "Người đàn ông, nhân viên"),
    Word("market", "Thị trường"),
    Word("mean", "Có nghĩa là, keo kiệt"),
    Word("meet", "Gặp, gặp gỡ"),
    Word("message", "Tin nhắn, thông điệp"),
    Word("middle", "Giữa"),
    Word("money", "Tiền"),
    Word("more", "Nhiều hơn"),
    Word("move", "Di chuyển, thay đổi"),
    Word("name", "Tên"),
    Word("night", "Ban đêm"),
    Word("note", "Ghi chú, nốt nhạc"),
    Word("open", "Mở, cởi mở"),
    Word("opinion", "Ý kiến"),
    Word("orange", "Quả cam, màu cam"),
    Word("order", "Đặt hàng, lệnh"),
    Word("other", "Khác"),
    Word("over", "Trên, kết thúc"),
    Word("paper", "Giấy, báo"),
    Word("part", "Phần, bộ phận"),
    Word("peace", "Hòa bình"),
    Word("people", "Người, dân"),
    Word("place", "Nơi, đặt"),
    Word("plan", "Kế hoạch, dự định"),
    Word("play", "Chơi, vở kịch"),
    Word("point", "Điểm, chỉ tay"),
    Word("poor", "Nghèo, xấu"),
    Word("position", "Vị trí, tư thế"),
    Word("possible", "Có thể, khả thi"),
    Word("power", "Sức mạnh, quyền lực"),
    Word("practice", "Thực hành, luyện tập"),
    Word("present", "Hiện tại, món quà"),
    Word("price", "Giá cả"),
    Word("problem", "Vấn đề"),
    Word("project", "Dự án, kế hoạch"),
    Word("protect", "Bảo vệ"),
    Word("public", "Công cộng"),
    Word("question", "Câu hỏi"),
    Word("quick", "Nhanh chóng"),
    Word("rain", "Mưa"),
    Word("reach", "Với tới, đạt được"),
    Word("read", "Đọc"),
    Word("ready", "Sẵn sàng"),
    Word("real", "Thật, thực tế"),
    Word("red", "Màu đỏ"),
    Word("remove", "Loại bỏ"),
    Word("repeat", "Lặp lại"),
    Word("report", "Báo cáo"),
    Word("result", "Kết quả"),
    Word("right", "Đúng, bên phải"),
    Word("road", "Đường"),
    Word("rock", "Đá, rock"),
    Word("run", "Chạy"),
    Word("safe", "An toàn"),
    Word("same", "Giống nhau"),
    Word("school", "Trường học"),
    Word("sea", "Biển"),
    Word("secret", "Bí mật"),
    Word("see", "Nhìn thấy"),
    Word("self", "Bản thân"),
    Word("sell", "Bán"),
    Word("send", "Gửi"),
    Word("service", "Dịch vụ"),
    Word("set", "Đặt, bộ, tập hợp"),
    Word("short", "Ngắn, thấp"),
    Word("show", "Trình chiếu, sự kiện"),
    Word("side", "Bên, mặt"),
    Word("simple", "Đơn giản"),
    Word("sing", "Hát"),
    Word("size", "Kích thước"),
    Word("sleep", "Ngủ"),
    Word("small", "Nhỏ"),
    Word("snow", "Tuyết"),
    Word("social", "Xã hội"),
    Word("song", "Bài hát"),
    Word("speak", "Nói"),
    Word("special", "Đặc biệt"),
    Word("sport", "Thể thao"),
    Word("start", "Bắt đầu"),
    Word("state", "Tiểu bang, trạng thái"),
    Word("stay", "Ở lại"),
    Word("store", "Cửa hàng"),
    Word("strong", "Mạnh mẽ"),
    Word("study", "Học tập"),
    Word("success", "Thành công"),
    Word("summer", "Mùa hè"),
    Word("supply", "Cung cấp"),
    Word("support", "Hỗ trợ"),
    Word("table", "Bàn, bảng"),
    Word("take", "Lấy, mang"),
    Word("talk", "Nói chuyện"),
    Word("team", "Đội, nhóm"),
    Word("test", "Bài kiểm tra, thử nghiệm"),
    Word("thank", "Cảm ơn"),
    Word("that", "Cái đó, đó"),
    Word("the", "Cái, các"),
    Word("think", "Suy nghĩ"),
    Word("this", "Cái này"),
    Word("time", "Thời gian"),
    Word("to", "Đến, với"),
    Word("top", "Cao, trên đỉnh"),
    Word("total", "Tổng cộng"),
    Word("travel", "Du lịch"),
    Word("tree", "Cây"),
    Word("trust", "Tin tưởng"),
    Word("try", "Cố gắng"),
    Word("turn", "Quay, lật"),
    Word("under", "Dưới"),
    Word("unit", "Đơn vị"),
    Word("use", "Sử dụng"),
    Word("very", "Rất"),
    Word("visit", "Thăm"),
    Word("voice", "Giọng nói"),
    Word("wait", "Chờ đợi"),
    Word("walk", "Đi bộ"),
    Word("want", "Muốn"),
    Word("warm", "Ấm"),
    Word("watch", "Xem, đồng hồ"),
    Word("water", "Nước"),
    Word("week", "Tuần"),
    Word("well", "Tốt, giếng"),
    Word("what", "Cái gì"),
    Word("where", "Ở đâu"),
    Word("which", "Cái nào"),
    Word("with", "Với"),
    Word("work", "Làm việc"),
    Word("world", "Thế giới"),
    Word("year", "Năm"),
    Word("yellow", "Màu vàng")
)

