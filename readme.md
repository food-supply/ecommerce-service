🔹 1. Lấy danh sách sản phẩm theo nhiều điều kiện
- Theo category

- Theo trạng thái (active/inactive)

- Theo tên (tìm kiếm)

- Theo khoảng giá

- Theo cửa hàng (người bán)

- Có phân trang (pagination), sắp xếp (sorting)

🔹 2. Lấy chi tiết sản phẩm
- API: GET /products/{id}

- Trả về thông tin chi tiết sản phẩm: mô tả, ảnh, giá, tồn kho, đánh giá,...

🔹 3. Quản lý tồn kho
- Cập nhật số lượng tồn

- Kiểm tra tồn kho trước khi đặt hàng

- Đánh dấu sản phẩm "hết hàng" nếu số lượng = 0

🔹 4. Quản lý trạng thái sản phẩm
- Active, Inactive, Archived

- Ẩn sản phẩm khỏi khách hàng nếu chưa sẵn sàng

🔹 5. Gợi ý sản phẩm liên quan
- Gợi ý sản phẩm cùng category

- Gợi ý theo người dùng thường xem/mua

🔹 6. Gắn tag, thuộc tính
- Gắn tag như: new, hot, discount

- Thêm thuộc tính động: màu sắc, size, chất liệu,...

🔹 7. Quản lý ảnh sản phẩm
- Upload ảnh đại diện, ảnh chi tiết

- Xoá, cập nhật ảnh

🔹 8. Quản lý giá
- Giá gốc, giá khuyến mãi

- Hỗ trợ giảm giá theo thời gian

🔹 9. Export / Import
- Export danh sách sản phẩm ra file Excel

- Import hàng loạt sản phẩm từ file

🔹 10. Audit & Lịch sử thay đổi
- Lưu lại các lần cập nhật thông tin sản phẩm

- Hiển thị ai đã chỉnh sửa, lúc nào

<!-- 🔹 11. API nội bộ
- Dành cho các service khác (order-service, inventory-service, v.v.)

- Ví dụ: GET /internal/products/{id} trả về dữ liệu đơn giản, không cần ảnh, mô tả dài.. -->

<!-- Alt + Z -->