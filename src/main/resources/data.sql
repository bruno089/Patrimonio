SET IDENTITY_INSERT [dbo].[category] ON
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (1, N'Sueldo', 1)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (2, N'Devolucion', 1)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (3, N'Venta', 1)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (4, N'Ingreso Extra', 1)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (5, N'Tarjeta De Credito', 0)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (6, N'Salud', 0)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (7, N'Ropa', 0)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (8, N'Comida', 0)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (9, N'Salidas a Comer', 0)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (10, N'Servicios', 0)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (11, N'Internet', 0)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (12, N'Impuestos', 0)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (13, N'Compras Varias', 0)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (14, N'Compra Dolares', 0)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (15, N'Prestamo', 0)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (16, N'Auto', 0)
INSERT [dbo].[Concepto] ([id], [nombre], [ingreso]) VALUES (17, N'Gimnasio', 0)
SET IDENTITY_INSERT [dbo].[category] OFF


SET IDENTITY_INSERT [dbo].[usuario] ON
INSERT [dbo].[Usuario] ([id], [nombre], [clave], [email], [activo], [registro]) VALUES (1, N'gguanca', N'$2a$10$whNl1qOH5Sk/VtW4uCjGMeeKCQhHv6O3Wxy3gxj07Ra6ynH0Ow2VG', N'ghgb@hotmail.com', 1, CAST(N'2022-07-14' AS Date))
INSERT [dbo].[Usuario] ([id], [nombre], [clave], [email], [activo], [registro]) VALUES (2, N'blopez', N'$2a$10$WDu0wIr.uTyarOnD2YG37.6s0iNZr845WoWZjJR/l6iyLYmr8nQV2', N'bruno@hotmail.com', 0, CAST(N'2022-07-14' AS Date))
SET IDENTITY_INSERT [dbo].[usuario] OFF
