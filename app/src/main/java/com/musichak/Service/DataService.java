package com.musichak.Service;

import com.musichak.Model.Album;
import com.musichak.Model.BaiHat;
import com.musichak.Model.ChuDe;
import com.musichak.Model.ChuDeTheLoai;
import com.musichak.Model.Playlist;
import com.musichak.Model.QuangCao;
import com.musichak.Model.TheLoai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {

    @GET("songbanner.php")
    Call<List<QuangCao>> GetDataBanner();
    @GET("playlistforcurrentday.php")
    Call<List<Playlist>> GetPlaylistCurrentDay();
    @GET("chudevatheloaitrongngay.php")
    Call<ChuDeTheLoai> GetCategoryMusic();
    @GET("albumhot.php")
    Call<List<Album>> GetAlbumHot();
    @GET("baihatyeuthich.php")
    Call<List<BaiHat>> GetListBaiHatYeuThich();
    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhSachBaiHatTheoQuangCao(@Field("idquangcao") String idquangcao);
    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhSachBaiHatTheoPlaylist(@Field("idplaylist") String idplaylist);
    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhSachBaiHatTheoTheLoai(@Field("idtheloai") String idtheloai);
    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhSachBaiHatTheoAlbum(@Field("idalbum") String idalbum);
    @FormUrlEncoded
    @POST("theloaitheochude.php")
    Call<List<TheLoai>> GetTheLoaiTheoChuDe(@Field("idchude") String idchude);
}
