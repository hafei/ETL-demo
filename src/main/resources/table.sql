IF OBJECT_ID('dbo.CUSIP_Info', 'U') IS NULL
BEGIN
CREATE TABLE [dbo].[CUSIP_Info] (
    [CUSIP] VARCHAR(9) PRIMARY KEY,
    [Issuer] VARCHAR(6),
    [IssueDate] DATE,
    [MaturityDate] DATE,
    [CouponRate] DECIMAL(5,2),
    [Price] DECIMAL(8,2),
    [ParValue] DECIMAL(8,2),
    [MarketValue] DECIMAL(10,2),
    [Rating] VARCHAR(3),
    [Sector] VARCHAR(30)
    )

--     BULK INSERT [dbo].[CUSIP_Info] FROM 'C:\path\to\fake_data.csv'
--     WITH (
--         FIELDTERMINATOR = ',',
--         ROWTERMINATOR = '\n',
--         FIRSTROW = 2
--         )
END