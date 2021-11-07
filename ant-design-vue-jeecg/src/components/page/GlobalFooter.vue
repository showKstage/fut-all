<template>
  <div class="footer">
    <div class="links">
      <a href="http://interview-cloud-web.aift.ftwhale.com:8082/online/cgformList/2c9481c47c5de44f017c5f6774710007" target="_blank">FUT 首页</a>
    </div>
    <div class="copyright">
      Copyright
      <a-icon type="copyright"/>
      2021 <span>WE 出品</span>
    </div>
  </div>
</template>

<script>
  export default {
    name: "LayoutFooter"
  }
</script>

<style lang="less" scoped>
  .footer {
    padding: 0 16px;
    margin: 48px 0 24px;
    text-align: center;

    .links {
      margin-bottom: 8px;

      a {
        color: rgba(0, 0, 0, .45);

        &:hover {
          color: rgba(0, 0, 0, .65);
        }

        &:not(:last-child) {
          margin-right: 40px;
        }
      }
    }
    .copyright {
      color: rgba(0, 0, 0, .45);
      font-size: 14px;
    }
  }
</style>